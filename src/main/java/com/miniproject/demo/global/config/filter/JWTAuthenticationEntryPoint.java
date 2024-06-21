package com.miniproject.demo.global.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.demo.global.response.BaseResponse;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(401);

        BaseResponse<Object> errorResponse =
                BaseResponse.onFailure(
                        ErrorStatus._UNAUTHORIZED.getCode(),
                        ErrorStatus._UNAUTHORIZED.getMessage(),
                        null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);
    }
}

