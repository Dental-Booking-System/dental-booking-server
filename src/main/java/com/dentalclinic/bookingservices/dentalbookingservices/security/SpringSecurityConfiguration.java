package com.dentalclinic.bookingservices.dentalbookingservices.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Autowired
    private FirebaseAuthFilter firebaseAuthFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain filterFirebaseChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->
                        authorize
                                .anyRequest()
                                .permitAll()
                )
                .addFilterAfter(firebaseAuthFilter, BasicAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->
                        authorize
                                .anyRequest()
                                .permitAll()
                )
        ;
        return http.build();
    }

    @Bean
    public FilterRegistrationBean<FirebaseAuthFilter> firebaseAuthFilterFilterRegistrationBean(FirebaseAuthFilter filter) {
        FilterRegistrationBean<FirebaseAuthFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
}

