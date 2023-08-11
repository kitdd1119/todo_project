package com.example.my.config.security.auth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.my.common.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_OK);
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");

    ResponseDTO<Object> responseDTO = ResponseDTO.builder()
        .code(0)
        .message("로그인 성공")
        .build();

    String body = objectMapper.writeValueAsString(responseDTO);

    response.getWriter().print(body);
  }

}
