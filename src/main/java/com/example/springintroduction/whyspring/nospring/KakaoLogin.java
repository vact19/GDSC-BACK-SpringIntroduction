package com.example.springintroduction.whyspring.nospring;

public class KakaoLogin implements SocialLogin{

    @Override
    public void socialLogin() {
        System.out.println("카카오 로그인");
    }
}
