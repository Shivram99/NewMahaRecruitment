package com.maharecruitment.gov.in.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;

@Configuration
public class SecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http
           // ModuleRepository moduleRepo,
            //CustomAccessDeniedHandler accessDeniedHandler,
            //CustomLoginSuccessHandler loginSuccessHandler,
            //CustomLogoutSuccessHandler logoutSuccessHandler
          
    ) throws Exception {

    	http.authorizeHttpRequests(auth -> {

    	   // List<Module> modules = moduleRepo.findAll();

    	   
    	    // Public URLs
    	    auth.requestMatchers(
    	            "/login", "/doLogin","/home",
    	            "/css/**", "/js/**", "/images/**",
    	            "/error/**"
    	    ).permitAll();

    	    //auth.requestMatchers("/", "/home").authenticated();

    	    auth.anyRequest().authenticated();
    	});

        // ✔ FIX: DO NOT let Spring handle authentication again
        http.formLogin(form -> form
                .loginPage("/login")
                .permitAll()
        );

        // insert captcha filter
       // http.addFilterBefore(captchaLoginFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout(logout -> logout
                .logoutUrl("/logout")
               // .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
        );

        http.sessionManagement(session -> session
                .sessionFixation().migrateSession()
                .invalidSessionUrl("/login?sessionExpired=true")
        );

        http.sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
        );

        http.exceptionHandling(ex -> ex
                //.accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint((req, res, authEx) -> {
                    System.out.println("ACCESS DENIED for URL: " + req.getRequestURI());
                    res.sendRedirect("/login?unauthenticated=true");
                })
        );

        http.headers(headers -> headers
                .httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).preload(true))
                .cacheControl(cache -> {})
                .addHeaderWriter(new CacheControlHeadersWriter())
        );

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
