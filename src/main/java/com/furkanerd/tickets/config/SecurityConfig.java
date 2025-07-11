package com.furkanerd.tickets.config;

import com.furkanerd.tickets.filter.UserProvisioningFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ,
                                                   UserProvisioningFilter userProvisioningFilter,
                                                   JwtAuthenticationConverter jwtAuthenticationConverter
    ) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/api/v1/published-events/**").permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/api/v1/events").hasRole("ORGANIZER")
                                .requestMatchers("/api/v1/ticket-validations").hasRole("STAFF")
                                .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class);
        return http.build();
    }
}

