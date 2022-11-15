package com.example.springintroduction.whyspring.spring;

import org.springframework.stereotype.Component;

@Component
public class UserRepository {
    public void save() {
        System.out.println("저장");
    }
}
