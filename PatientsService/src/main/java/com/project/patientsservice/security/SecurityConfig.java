package com.project.patientsservice.security;

/**
 * Project Name: micro
 * File Name: d
 * Created by: DELL
 * Created on: 2/4/2025
 * Description:
 * <p>
 * d is a part of the micro project.
 */


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled  = true)
public class SecurityConfig {

    private JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity.
                cors(Customizer.withDefaults())
                .sessionManagement(
                        sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                csrf(csrf->csrf.disable())
                //.authorizeHttpRequests(ar->ar.requestMatchers("/api/clients/**").hasAuthority("USER"))
                .authorizeHttpRequests(ar->ar
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        .anyRequest().permitAll()).
                oauth2ResourceServer(oauth->oauth.jwt(
                        jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter)
                ))

                .build() ;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
