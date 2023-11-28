package com.example.ecommerce.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
// Ghi đè onAuthenticationFailure của AuthenticationFailureHandler để xử lí sự cố xác thực trong spring security
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
// Chuyển đối tượng Java thành JSON và ngược lại
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
// Tạo map chứa thông tin sự cố gồm hai khóa
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Authentication failed");
        errorResponse.put("message", "Your custom error message here");
// Ghi phản hồi
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}