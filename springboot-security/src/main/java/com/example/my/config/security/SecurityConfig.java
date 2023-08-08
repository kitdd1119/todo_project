package com.example.my.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final AuthenticationSuccessHandler authenticationSuccessHandler;
        private final AuthenticationFailureHandler authenticationFailureHandler;
        private final LogoutSuccessHandler logoutSuccessHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

                httpSecurity.csrf(config -> config.disable());

                // httpSecurity.headers(config -> config
                // .frameOptions(frameOptionsConfig -> frameOptionsConfig
                // .disable()
                // )
                // );
                //
                // httpSecurity.authorizeHttpRequests(config -> config
                // .requestMatchers(PathRequest.toH2Console())
                // .permitAll()
                // );

                httpSecurity.authorizeHttpRequests(config -> config
                                .requestMatchers("/css/**", "/js/**", "/assets/**", "/favicon.ico")
                                // requestMatchers 안에 적은 주소 뒤로 들어오는 것들은 모두
                                .permitAll()
                                // 허용한다.
                                .requestMatchers("/js/admin*.js", "/h2/**", "/temp/**")
                                .hasRole("ADMIN")
                // ADMIN 권한이 있는 사람만 허용하는 requestMatchers.
                );

                httpSecurity.authorizeHttpRequests(config -> config
                                .requestMatchers("/auth/**", "/api/*/auth/**")
                                // 매칭되는 주소들
                                .permitAll()
                                // 모두 허용한다.
                                .requestMatchers("/admin/**", "/api/*/admin/**")
                                // 매칭되는 주소들
                                .hasRole("ADMIN")
                                // ADMIN 권한이 있는 사람만 허용한다.
                                .anyRequest()
                                // 그 외의 모든 요청(주소)은
                                // 인증(authentication)된(로그인된) 사용자만 허용한다.
                                .authenticated());

                httpSecurity.formLogin(config -> config
                                // formLogin() 메소드는 로그인 폼을 사용하겠다는 설정이다.
                                // login.html 안에 form태그에 가장 중요한 것은 name이다.
                                .loginPage("/auth/login")
                                // 로그인 페이지의 주소를 설정한다.
                                .loginProcessingUrl("/api/v1/auth/login")
                                // 로그인 페이지에서 로그인 버튼을 누르면(로그인 api 요청 시 사용할 주소)
                                // 이 주소로 요청이 들어온다.
                                .usernameParameter("id")
                                // 로그인 페이지의 form태그 안에 있는 input태그의 name이 id인 것을
                                // usernameParameter() 메소드에 인자로 넣어준다.
                                .passwordParameter("password")
                                // 로그인 페이지의 form태그 안에 있는 input태그의 name이 password인 것을
                                // passwordParameter() 메소드에 인자로 넣어준다.
                                .successHandler(authenticationSuccessHandler)
                                // 로그인 성공 시 실행할 핸들러를 등록한다.(유저에게 보낼 내용)
                                .failureHandler(authenticationFailureHandler)
                                // 로그인 실패 시 실행할 핸들러를 등록한다.(유저에게 보낼 내용)
                                // 모두 허용
                                .permitAll());

                httpSecurity.logout(config -> config
                                .logoutUrl("/auth/logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessHandler(logoutSuccessHandler)
                                .permitAll());

                return httpSecurity.getOrBuild();
        }

}
