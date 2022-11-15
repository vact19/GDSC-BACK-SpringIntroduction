package com.example.springintroduction.whyspring.nospring;

public class NaverLogin implements SocialLogin{

    @Override
    public void socialLogin() {
        System.out.println("네이버 로그인");
    }
}
