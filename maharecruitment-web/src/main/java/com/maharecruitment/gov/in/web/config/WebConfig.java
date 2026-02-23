package com.maharecruitment.gov.in.web.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.maharecruitment.gov.in.web.interceptor.BreadcrumbInterceptor;




@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private BreadcrumbInterceptor breadcrumbInterceptor;

   
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(breadcrumbInterceptor)
                .addPathPatterns("/**")          // apply everywhere
                .excludePathPatterns("/api/**")  // ❌ exclude all API endpoints
                .excludePathPatterns("/rest/**")
                .excludePathPatterns("/v3/api-docs/**")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/swagger-ui.html");
    }

    
}
