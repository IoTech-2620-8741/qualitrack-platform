package com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.pipeline;

import com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.services.TenantAccess;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import java.util.Map;

@Component
public class TenantResourceInterceptor implements HandlerInterceptor {
    private final TenantAccess access;
    public TenantResourceInterceptor(TenantAccess access) { this.access = access; }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var variables = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (variables instanceof Map<?, ?> paths) {
            paths.forEach((key, value) -> check(key.toString(), value.toString()));
        }
        request.getParameterMap().forEach((key, values) -> {
            for (var value : values) check(key, value);
        });
        return true;
    }

    private void check(String name, String value) {
        if (access.recognizes(name)) access.require(name, Long.valueOf(value));
    }
}
