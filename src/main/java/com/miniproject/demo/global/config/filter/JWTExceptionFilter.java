package com.miniproject.demo.global.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.demo.global.error.handler.AuthHandler;
import com.miniproject.demo.global.response.BaseResponse;
import com.miniproject.demo.global.response.code.status.ErrorStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthHandler e) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(e.getErrorReasonHttpStatus().getHttpStatus().value());

            ErrorStatus code = (ErrorStatus) e.getCode();

            BaseResponse<Object> errorResponse =
                    BaseResponse.onFailure(code.getCode(), code.getMessage(), null);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), errorResponse);
        }

    }
}
