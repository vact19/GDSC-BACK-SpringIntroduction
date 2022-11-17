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

    // @PostConstruct 스프링 애플리케이션 시작 직후 의존성 주입이 끝나면 실행됨
    @PostConstruct
    // nospring 패키지의 LoginController.java 파일의 main() 메소드와는 다른 아주 간단한 한줄코드
    // public static void main() 메소드 대신 사용하였다.
    public void login(){
        login.doLogin();
    }


}
