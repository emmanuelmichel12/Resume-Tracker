package com.jobtracker.auth_service.config;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.jobtracker.auth_service.repository.ResumeUserRepository;
import java.util.Collections;
//import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtUtilAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ResumeUserRepository userRepository;

    public JwtUtilAuthFilter(JwtUtil jwtUtil, ResumeUserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        try {
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
                    && jwtUtil.validateToken(token)) {
                userRepository.findByUsername(username).ifPresent(user -> {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user.getUsername(), // becomes Authentication.getName()
                            null, // no credentials needed past this point
                            Collections.emptyList() // no roles for now
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                });
            }
        } catch (Exception e) {
            // Invalid token - just proceed without setting authentication
        }
        filterChain.doFilter(request, response);
    }

}
