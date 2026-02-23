package com.maharecruitment.gov.in.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Hello HRMS Base Module");
        return "index";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("message", "Hello HRMS Base Module");
        return "login";
    }
    
    @GetMapping("/")
    public String root(Model model) {
    	  model.addAttribute("message", "Hello HRMS Base Module");
          return "index";
    }
    
	/*
	 * @GetMapping("/logout") public String logout(HttpSession session) {
	 * session.invalidate(); return "redirect:/login?logout"; }
	 */
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Remove all cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0); // expire immediately
                response.addCookie(cookie);
            }
        }

        // Redirect to login page with logout param
        return "redirect:/login?logout";
    }
    
}
