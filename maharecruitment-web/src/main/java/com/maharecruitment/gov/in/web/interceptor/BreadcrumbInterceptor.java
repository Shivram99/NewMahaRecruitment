package com.maharecruitment.gov.in.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class BreadcrumbInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI(); // /attendance/home
        Map<String, String> breadcrumb = new LinkedHashMap<>();

        breadcrumb.put("Home", "/home");

        String[] parts = uri.split("/");
        String path = "";

        for (String part : parts) {
            if (part.isBlank()) continue;

            path += "/" + part;
            String label = Character.toUpperCase(part.charAt(0)) + part.substring(1);

            breadcrumb.put(label, path);
        }

        request.setAttribute("breadcrumb", breadcrumb);
        return true;
    }
}
