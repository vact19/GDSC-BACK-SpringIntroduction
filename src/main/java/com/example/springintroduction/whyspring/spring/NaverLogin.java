package com.example.springintroduction.whyspring.spring;

import org.springframework.stereotype.Component;

@Component
public class NaverLogin implements SocialLogin {

    @Override
    public void socialLogin() {
        System.out.println("네이버 로그인");
    }
}
