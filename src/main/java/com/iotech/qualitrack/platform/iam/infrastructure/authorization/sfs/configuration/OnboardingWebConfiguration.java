package com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.configuration;

import com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.pipeline.OnboardingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OnboardingWebConfiguration implements WebMvcConfigurer {
    private final OnboardingInterceptor interceptor;
    private final com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.pipeline.TenantResourceInterceptor tenant;

    public OnboardingWebConfiguration(OnboardingInterceptor interceptor,
            com.iotech.qualitrack.platform.iam.infrastructure.authorization.sfs.pipeline.TenantResourceInterceptor tenant) {
        this.interceptor = interceptor;
        this.tenant = tenant;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/api/v1/**");
        registry.addInterceptor(tenant).addPathPatterns("/api/v1/**");
    }
}
