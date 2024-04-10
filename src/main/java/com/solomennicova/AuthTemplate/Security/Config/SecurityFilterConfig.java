package com.solomennicova.AuthTemplate.Security.Config;

import com.solomennicova.AuthTemplate.Security.AuthProvider;
import com.solomennicova.AuthTemplate.Security.Filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityFilterConfig {

    private final AuthProvider authProvider;

    public SecurityFilterConfig(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/registration")
                        .hasRole("ADMIN")
                        .requestMatchers("/api/auth/validateToken")
                        .authenticated()
                        .requestMatchers("/api/auth/login")
                        .permitAll()
                        .requestMatchers("/api/site/beer/all")
                        .permitAll()
                        .requestMatchers("/api/user/all", "/api/user/delete", "/api/user/update")
                        .hasRole("ADMIN")
                        .requestMatchers("/api/admin/beer/add", "/api/admin/beer/update", "/api/admin/vacancy/add", "/api/admin/vacancy/update", "/api/admin/beer/delete/", "/api/admin/vacancy/delete/")
                        .hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtAuthFilter(authProvider), BasicAuthenticationFilter.class)
                .build();
    }

}
