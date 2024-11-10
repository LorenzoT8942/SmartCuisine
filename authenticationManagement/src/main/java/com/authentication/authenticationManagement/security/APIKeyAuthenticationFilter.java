package com.authentication.authenticationManagement.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class APIKeyAuthenticationFilter implements Filter{

    @Value("${authentication.service.apikey}")
    private String validApiKey;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        // Retrieve API key from header
        String requestApiKey = httpRequest.getHeader("Authorization");

        // Check if the API key is valid
        
        if (requestApiKey == null || requestApiKey.isEmpty()) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not setted any API Key");
        }
        else if (validApiKey.equals(requestApiKey)) {
            chain.doFilter(request, response); // Continue to the next filter or target
        } else {
            // Invalid API key
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
        }
    }

    @Override
    public void destroy() {
    }

}

