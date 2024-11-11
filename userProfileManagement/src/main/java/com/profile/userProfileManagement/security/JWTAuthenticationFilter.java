package com.profile.userProfileManagement.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.profile.userProfileManagement.dtos.responses.JWTValidateResponse;
import com.profile.userProfileManagement.utilities.JWTContext;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter implements Filter{

    
    @Value("${external.api.auth_service.validate}")
    private String getValidateUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

         // Skip the filter for these paths
        if ("/profiles/create".equals(httpRequest.getRequestURI()) || "/profiles/login".equals(httpRequest.getRequestURI())) {
            chain.doFilter(request, response); 
            return;
        }

        // Retrieve JWT token from header
        String token = httpRequest.getHeader("Authorization");
        if(token == null || token.isEmpty()){
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not setted any JWT token");
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", token);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        // Make the GET request
        ResponseEntity<JWTValidateResponse> userJWTDto = restTemplate.exchange(getValidateUrl, HttpMethod.GET, requestEntity, JWTValidateResponse.class);
        if(userJWTDto.getStatusCode().isError() || !userJWTDto.hasBody()){
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "error in validating the JWT token");
        }
        else if (userJWTDto.getBody().isValid()) {
            JWTContext.set(userJWTDto.getBody().getMessage());
            chain.doFilter(request, response);
        } else {
            // Invalid API key
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, userJWTDto.getBody().getMessage());
        }
    }

    @Override
    public void destroy() {

        JWTContext.clear();

    }

}
