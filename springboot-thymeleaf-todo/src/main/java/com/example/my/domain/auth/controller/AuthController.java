package com.example.my.domain.auth.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AuthController {

    // @GetMapping("/auth/login")
    // public ModelAndView login() {
    // ModelAndView modelAndView = new ModelAndView();
    // modelAndView.setViewName("auth/login");
    // return modelAndView;
    // }

    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    } // auth/login 은 viewname이라 위에 주석처리 한 코드와 같은 동작방식으로 처리한다.

    @GetMapping("/auth/join")
    public String join() {
        return "auth/join";
    }

    @GetMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // session안에 있는 데이터를 모두 날리겠다(로그아웃)
        return "auth/logout";
    }
}
