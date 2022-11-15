package com.example.springintroduction.whyspring.spring;

import org.springframework.stereotype.Component;

@Component
public class TokenManager {
    public void generateToken(){
        System.out.println("토큰 생성");
    }
}
