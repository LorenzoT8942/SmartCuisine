package com.profile.userProfileManagement.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTFilterConfig {
    @Bean
    public FilterRegistrationBean<JWTAuthenticationFilter> apiKeyFilterRegistration(JWTAuthenticationFilter apiKeyFilter) {
        FilterRegistrationBean<JWTAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(apiKeyFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
