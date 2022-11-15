package com.example.springintroduction.whyspring.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Login {

    // SocialLogin 은 인터페이스
    private final SocialLogin socialLogin;

    // 로그인 유지용 토큰 생성기
    private final TokenManager tokenManager;

    // 사용자 정보 저장 클래스
    private final User user;

    // 생성자
    @Autowired
    public Login(SocialLogin socialLogin, TokenManager tokenManager, User user) {
        this.socialLogin = socialLogin;
        this.tokenManager = tokenManager;
        this.user = user;
    }

    // 로그인 작업 시작
    public void doLogin(){
        System.out.println("===============시작===============");
        socialLogin.socialLogin();
        tokenManager.generateToken();
        user.save();
        System.out.println("===============종료===============");
    }
}
