package com.solomennicova.AuthTemplate.Security.Filter;

import com.solomennicova.AuthTemplate.Security.AuthProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@Order(0)
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthProvider authProvider;

    public JwtAuthFilter(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (request.getRequestURL().toString().contains("swagger") || request.getRequestURL().toString().contains("api-docs")
                || request.getRequestURL().toString().contains("ping")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token;
        String authorizationHeader = request.getHeader("Authorization");

        logger.info("Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);

            if (token.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    Authentication authToken = authProvider.validateToken(token);

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
