package com.notification.notificationManagement.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiFilterConfig {


    @Bean
    public FilterRegistrationBean<APIKeyAuthenticationFilter> apiKeyFilterRegistration(APIKeyAuthenticationFilter apiKeyFilter) {
        FilterRegistrationBean<APIKeyAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(apiKeyFilter);
        registrationBean.addUrlPatterns("/*"); 
        return registrationBean;
    }

}
