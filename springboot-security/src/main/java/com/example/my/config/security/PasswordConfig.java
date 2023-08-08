package com.example.my.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder: 10자리의 암호화된 문자열을 반환
        // BCryptPasswordEncoder 이것 말고 다른 패스워드인코더가 많다.
        return new BCryptPasswordEncoder();
        // 이 패스워드 인코더가 컨포넌트가 된다.
    }

}
