package com.jpsouza.webcrawler.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jpsouza.webcrawler.errors.ErrorDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e)
            throws IOException {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(401);
        response.setStatus(errorDetails.getStatus());
        errorDetails.setTimestamp(new Date());
        errorDetails.setException(e);
        errorDetails.setDetails(e.getMessage());
        errorDetails.setMessage("Token não informado ou não inválido");
        logger.error(null, e);
        OutputStream out = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.writeValue(out, errorDetails);
        out.flush();
    }
}
