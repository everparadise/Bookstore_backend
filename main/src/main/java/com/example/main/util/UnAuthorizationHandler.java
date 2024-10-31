package com.example.main.util;

import com.example.main.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UnAuthorizationHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ObjectMapper om = new ObjectMapper();
        ResponseDto<Boolean> dto = new ResponseDto<>(false, "Authorization Failure", null);

        // 组装响应
        String responseValue = om.writeValueAsString(dto);
        response.setContentType("application/json");
        response.setStatus(HTTPResponse.SC_UNAUTHORIZED);
        response.getWriter().write(responseValue);
    }
}
