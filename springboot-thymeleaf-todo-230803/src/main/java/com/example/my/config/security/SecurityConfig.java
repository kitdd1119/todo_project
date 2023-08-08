package com.example.my.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.my.config.security.auth.CustomAuthenticationFailureHandler;
import com.example.my.config.security.auth.CustomAuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
  private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    // csrf 보안 해제 (실무에서는 보안 적용 권장 - 지금은 테스트 용임.)
    httpSecurity.csrf(
        // 일반적으로 config가 아닌 정식명칭 CsrfConfigurer 이렇게 작성한다.
        config -> config.disable());

    // 요청 주소 인증 및 인가 세팅
    httpSecurity.authorizeHttpRequests(
        config -> config
            .requestMatchers("/css/**", "/js/**", "/img/**")
            // 위에 해당하는 주소를 인증 및 인가없이 접속 가능하도록 한다.
            .permitAll()
            .requestMatchers("/auth/**", "/api/*/auth/**")
            .permitAll()
            .requestMatchers("/admin/**", "api/*/auth/**")
            // 위에 해당하는 주소는 ADMIN 권한이 있는 사람만 접근 가능하도록 한다.
            .hasRole("ADMIN")
            .anyRequest() // 나머지 모든 주소
            // 위에 해당하는 주소는 로그인이 되어 있어야만 접근 가능
            .authenticated()

    );
    // .anyRequest()
    // .authenticated());
    // 이것은 페이지가 많으면 계속 이어 붙일 수 있다.

    // 외부에서 접근할 로그인 세팅
    httpSecurity.formLogin(
        config -> config
            // 실제 로그인 컨트롤러 메소드 경로
            .loginPage("/auth/login") // 로그인 페이지 주소
            // 가상의 주소를 넣는다.
            // 로그인 페이지에서 로그인 버튼을 눌렀을 때 이동하는 주소
            .loginProcessingUrl("/api/v1/auth/login")
            .usernameParameter("id")
            .passwordParameter("password")
            .successHandler(customAuthenticationSuccessHandler)
            .failureHandler(customAuthenticationFailureHandler)
            .permitAll()

    );

    return httpSecurity.getOrBuild();
  }

}
