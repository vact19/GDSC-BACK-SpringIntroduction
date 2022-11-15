package com.example.springintroduction.whyspring.nospring;

public class User {

    private final UserRepository userRepository;

    public User(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(){
        userRepository.save();
    }
}
