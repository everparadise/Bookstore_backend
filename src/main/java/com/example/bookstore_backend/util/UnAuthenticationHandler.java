package com.example.bookstore_backend.util;

import com.example.bookstore_backend.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UnAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        //组装返回响应
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ResponseDto<Boolean> responseObject = new ResponseDto<>(false, "Authentication Failure", null);
        ObjectMapper objectMapper = new ObjectMapper();

        String responseValue = objectMapper.writeValueAsString(responseObject);
        response.resetBuffer();
        response.getWriter().write(responseValue);
        response.flushBuffer();
        //response.sendRedirect("http://localhost:3000");
    }
}
