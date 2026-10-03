package com.iotech.qualitrack.platform;

import com.iotech.qualitrack.platform.iam.interfaces.acl.IamContextFacade;
import com.iotech.qualitrack.platform.subscription.domain.model.aggregates.Subscription;
import com.iotech.qualitrack.platform.subscription.domain.model.commands.ActivateSubscriptionCommand;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.BillingCycle;
import com.iotech.qualitrack.platform.subscription.domain.model.valueobjects.PlanCode;
import com.iotech.qualitrack.platform.subscription.domain.repositories.SubscriptionRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the Product Batch Management API (TS61-TS72) through HTTP.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductBatchIntegrationTests {
    @LocalServerPort int port;
    @Autowired SubscriptionRepository subscriptions;
    @Autowired IamContextFacade iam;
    @Autowired PlatformTransactionManager transactions;
    private final HttpClient http = HttpClient.newHttpClient();
    private static final AtomicLong ruc = new AtomicLong(20700000000L);

    private record Account(Long id, String token) { }

    private record Plant(Account manager, long lab, long production) {
        String products() { return "/laboratories/" + lab + "/environments/" + production + "/products"; }
    }

    @Test
    void qualityManagerRegistersListsAndReadsProductsOfAnEnvironment() throws Exception {
        var plant = plant();
        var token = plant.manager().token();
        assertThat(call("GET", plant.products(), token, null).body()).isEqualTo("[]");

        var created = call("POST", plant.products(), token, product("PRD-ASP-500", "Aspirin 500mg"));
        assertThat(created.statusCode()).withFailMessage(created.body()).isEqualTo(201);
        long productId = id(created);
        assertThat(created.headers().firstValue("Location")).hasValueSatisfying(location ->
                assertThat(location).endsWith(plant.products() + "/" + productId));
        assertThat(created.body()).contains("\"environmentId\":" + plant.production()).contains("\"active\":true");

        assertThat(JsonPath.<List<Integer>>read(call("GET", plant.products(), token, null).body(), "$[*].id"))
                .containsExactly((int) productId);
        assertThat(call("GET", plant.products() + "/" + productId, token, null).statusCode()).isEqualTo(200);

        long storage = environment(plant, "WH-PT");
        var otherEnvironment = "/laboratories/" + plant.lab() + "/environments/" + storage + "/products";
        assertThat(call("GET", otherEnvironment, token, null).body()).isEqualTo("[]");
        assertThat(call("GET", otherEnvironment + "/" + productId, token, null).statusCode()).isEqualTo(404);
    }

    @Test
    void productCodesAndNamesAreUniqueInTheLaboratoryOnly() throws Exception {
        var plant = plant();
        var token = plant.manager().token();
        assertThat(call("POST", plant.products(), token, product("PRD-1", "Ibuprofen 400mg")).statusCode()).isEqualTo(201);

        long storage = environment(plant, "WH-PT");
        var otherEnvironment = "/laboratories/" + plant.lab() + "/environments/" + storage + "/products";
        assertThat(call("POST", otherEnvironment, token, product("PRD-1", "Other name")).statusCode()).isEqualTo(409);
        assertThat(call("POST", plant.products(), token, product("PRD-2", "Ibuprofen 400mg")).statusCode()).isEqualTo(409);
        assertThat(call("POST", plant.products(), token, product("PRD-3", " ")).statusCode()).isEqualTo(400);

        var otherPlant = plant();
        assertThat(call("POST", otherPlant.products(), otherPlant.manager().token(), product("PRD-1", "Ibuprofen 400mg"))
                .statusCode()).isEqualTo(201);
    }

    @Test
    void onlyQualityRolesRegisterProductsAndOtherLaboratoriesCannotReadThem() throws Exception {
        var plant = plant();
        var operator = operatorOf(plant);
        assertThat(call("POST", plant.products(), operator.token(), product("PRD-OP", "Operator product")).statusCode()).isEqualTo(403);
        assertThat(call("GET", plant.products(), operator.token(), null).statusCode()).isEqualTo(200);

        var outsider = plant();
        assertThat(call("GET", plant.products(), outsider.manager().token(), null).statusCode()).isEqualTo(403);
    }

    private static String product(String code, String name) {
        return """
                {"code":"%s","name":"%s","description":"Test product","specifications":"Tablet, blister pack"}
                """.formatted(code, name);
    }

    private Plant plant() throws Exception {
        var manager = account("ROLE_QA_MANAGER");
        var unique = UUID.randomUUID().toString();
        var end = OffsetDateTime.now().plusDays(10);
        subscriptions.save(Subscription.activate(new ActivateSubscriptionCommand(manager.id(), null,
                PlanCode.BASIC, BillingCycle.MONTHLY, "cus_fixture_" + unique, "sub_fixture_" + unique,
                "cs_fixture_" + unique, end.minusMonths(1).toString(), end.toString())));
        var created = call("POST", "/laboratories", manager.token(), """
                {"name":"Product laboratory %s","ruc":"%s","address":"Test address","phone":"999123456",
                "applicableRegulations":["BPM"]}
                """.formatted(unique, ruc.incrementAndGet()));
        assertThat(created.statusCode()).withFailMessage(created.body()).isEqualTo(201);
        long lab = id(created);
        var production = call("POST", "/laboratories/" + lab + "/environments", manager.token(),
                "{\"code\":\"PROD-01\",\"name\":\"Production area\",\"usage\":\"PRODUCTION\"}");
        assertThat(production.statusCode()).withFailMessage(production.body()).isEqualTo(201);
        return new Plant(manager, lab, id(production));
    }

    private long environment(Plant plant, String code) throws Exception {
        var created = call("POST", "/laboratories/" + plant.lab() + "/environments", plant.manager().token(),
                "{\"code\":\"" + code + "\",\"name\":\"Environment " + code + "\"}");
        assertThat(created.statusCode()).withFailMessage(created.body()).isEqualTo(201);
        return id(created);
    }

    private Account operatorOf(Plant plant) throws Exception {
        var operator = account("ROLE_LAB_OPERATOR");
        new TransactionTemplate(transactions).executeWithoutResult(status -> iam.assignLaboratory(operator.id(), plant.lab()));
        return operator;
    }

    private Account account(String role) throws Exception {
        var username = "product-" + UUID.randomUUID();
        var registration = call("POST", "/authentication/sign-up", null, """
                {"username":"%s","password":"TestPassword123!","roles":["%s"],"laboratoryId":null}
                """.formatted(username, role));
        assertThat(registration.statusCode()).withFailMessage(registration.body()).isEqualTo(201);
        var response = call("POST", "/authentication/sign-in", null, """
                {"username":"%s","password":"TestPassword123!"}
                """.formatted(username));
        assertThat(response.statusCode()).withFailMessage(response.body()).isEqualTo(200);
        return new Account(((Number) JsonPath.read(response.body(), "$.id")).longValue(), JsonPath.read(response.body(), "$.token"));
    }

    private static long id(HttpResponse<String> response) {
        return ((Number) JsonPath.read(response.body(), "$.id")).longValue();
    }

    private HttpResponse<String> call(String method, String path, String token, String body) throws Exception {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/v1" + path))
                .header("Content-Type", "application/json");
        if (token != null) request.header("Authorization", "Bearer " + token);
        request.method(method, body == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body));
        return http.send(request.build(), HttpResponse.BodyHandlers.ofString());
    }
}
