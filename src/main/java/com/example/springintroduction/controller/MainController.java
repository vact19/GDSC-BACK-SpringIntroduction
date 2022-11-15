package com.example.springintroduction.controller;

import com.example.springintroduction.member.Member;
import com.example.springintroduction.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @GetMapping("/")
    public String mainPage(Model model){
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        // model.addAttribute(members); // attributeName memberList
        return "home";
    }

    @PostConstruct
    public void createDummyData(){
        memberService.save(new Member("상남자", "1234"));
        memberService.save(new Member("하남자", "1234"));
    }
}
