package com.iotech.qualitrack.platform.laboratory.interfaces.rest;

import com.iotech.qualitrack.platform.laboratory.application.commandservices.ProductCommandService;
import com.iotech.qualitrack.platform.laboratory.application.queryservices.ProductQueryService;
import com.iotech.qualitrack.platform.laboratory.domain.model.queries.GetProductsByLabIdQuery;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.CreateProductResource;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.resources.PharmaceuticalProductResource;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import com.iotech.qualitrack.platform.laboratory.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import com.iotech.qualitrack.platform.shared.interfaces.rest.resources.MessageResource;
import com.iotech.qualitrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/laboratories/{laboratoryId}/products", produces = APPLICATION_JSON_VALUE)
public class LaboratoryProductsController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    public LaboratoryProductsController(ProductCommandService productCommandService, ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(
            @PathVariable Long laboratoryId,
            @RequestBody CreateProductResource resource
    ) {
        var command = CreateProductCommandFromResourceAssembler.toCommandFromResource(laboratoryId, resource);

        var result = productCommandService.handle(command)
                .map(productId -> new MessageResource("Product created successfully with ID: " + productId));

        return ResponseEntityAssembler.toResponseEntityFromResult(result, message -> message, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PharmaceuticalProductResource>> getProductsByLaboratoryId(
            @PathVariable Long laboratoryId
    ) {
        var products = productQueryService.handle(new GetProductsByLabIdQuery(laboratoryId));

        var resources = products.stream()
                .map(ProductResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}