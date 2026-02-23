package com.maharecruitment.gov.in.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.maharecruitment.gov.in.common.util.CookieUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    // Mapping: ROLE → Module URL
    private static final Map<String, String> ROLE_REDIRECT_MAP = new HashMap<>();

    static {
        ROLE_REDIRECT_MAP.put("ROLE_PENSION_MANAGER", "/pension");
        ROLE_REDIRECT_MAP.put("ROLE_ESERVICEBOOK_MANAGER", "/eservicebook");
        ROLE_REDIRECT_MAP.put("ROLE_HRMS_MANAGER", "/hrms");
        ROLE_REDIRECT_MAP.put("ROLE_PAYROLL_MANAGER", "/payroll");
        ROLE_REDIRECT_MAP.put("ROLE_COMMON_MANAGER", "/common");
        ROLE_REDIRECT_MAP.put("ROLE_ATTENDANCE_MANAGER", "/attendance");
        ROLE_REDIRECT_MAP.put("ROLE_ADMIN", "/admin");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // Debug logging to console
        System.out.println("=== LOGIN SUCCESS ===");
        System.out.println("User: " + authentication.getName());
        System.out.println("Session ID: " + request.getSession().getId());
        System.out.println("=====================");

        // Add Secure Cookie
        response.addCookie(
                CookieUtil.createSecureCookie("LOGIN_OK", "YES", 3600)
        );

        String continueParam = request.getParameter("continue");

        // Default redirect (VERY IMPORTANT – prevents redirecting to "/")
        String redirectUrl = "/home";

        // 1️⃣ Role-based redirect
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();

            if (ROLE_REDIRECT_MAP.containsKey(role)) {

                String moduleUrl = ROLE_REDIRECT_MAP.get(role);
                redirectUrl = moduleUrl;

                // 2️⃣ Continue param allowed only inside module
                if (continueParam != null && continueParam.startsWith(moduleUrl)) {
                    redirectUrl = continueParam;
                }

                break; // stop at first matching role
            }
        }

        // 3️⃣ FINAL IMPORTANT FIX:
        //    If redirectUrl becomes "/", override it.
        if (redirectUrl.equals("/") || redirectUrl.equals("")) {
            redirectUrl = "/home";  // Prevents 403 + ANONYMOUS bug
        }

        System.out.println("Redirecting user to: " + redirectUrl);

        response.sendRedirect(redirectUrl);
    }
}
