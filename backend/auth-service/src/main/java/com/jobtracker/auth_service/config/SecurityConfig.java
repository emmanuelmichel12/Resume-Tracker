package com.jobtracker.auth_service.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import com.jobtracker.auth_service.config.JwtUtilAuthFilter;

@Configuration
public class SecurityConfig {

    private final JwtUtilAuthFilter jwtUtilAuthFilter;

    public SecurityConfig(JwtUtilAuthFilter jwtUtilAuthFilter) {
        this.jwtUtilAuthFilter = jwtUtilAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public FilterRegistrationBean<JwtUtilAuthFilter> jwtFilterRegistration(JwtUtilAuthFilter filter) {
        FilterRegistrationBean<JwtUtilAuthFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
}
