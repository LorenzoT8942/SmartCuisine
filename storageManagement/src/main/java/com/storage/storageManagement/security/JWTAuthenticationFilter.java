package com.storage.storageManagement.security;


import com.storage.storageManagement.dtos.response.JWTValidateResponse;
import com.storage.storageManagement.utilities.JWTContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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



        // Retrieve JWT token from header
        String token = httpRequest.getHeader("Authorization");
        if(token == null || token.isEmpty()){
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not setted any JWT token");
            return;
        }
        System.out.println("the token of the user to be validated is "+token);

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
