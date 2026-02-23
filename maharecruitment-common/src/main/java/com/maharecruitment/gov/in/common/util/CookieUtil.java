package com.maharecruitment.gov.in.common.util;


import jakarta.servlet.http.Cookie;

public class CookieUtil {

    // Create secure cookie
    public static Cookie createSecureCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setSecure(true);                // HTTPS only
        cookie.setHttpOnly(true);              // JS cannot access cookie
        cookie.setAttribute("SameSite", "Strict");  // CSRF protection
        return cookie;
    }

    // Delete cookie securely
    public static Cookie deleteSecureCookie(String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "Strict");
        return cookie;
    }
}
