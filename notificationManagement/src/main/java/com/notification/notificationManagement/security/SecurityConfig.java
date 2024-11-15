package com.notification.notificationManagement.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private APIKeyAuthenticationFilter ApiFilter;

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection globally
            .csrf().disable()
            // Allow all routes to be accessed without authentication
            .authorizeRequests()
                .requestMatchers("/**").permitAll()
            .and()
            .addFilterBefore(ApiFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}