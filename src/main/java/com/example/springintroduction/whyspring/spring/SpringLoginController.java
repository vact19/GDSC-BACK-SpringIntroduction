package com.example.springintroduction.whyspring.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 *  로그인 작업을 수행한다.
 *  Login 클래스는 SocialLogin, TokenManager, User 를 사용한다.
 *      User 는 UserRepository 를 사용한다.
 */
@Controller
public class SpringLoginController {

    private final Login login;

    // 생성자
    @Autowired
    public SpringLoginController(Login login) {
        this.login = login;
    }

    // 스프링 애플리케이션 시작 직후 실행됨
    @PostConstruct
    // main() 메소드 대신
    public void login(){
        login.doLogin();
    }


}
