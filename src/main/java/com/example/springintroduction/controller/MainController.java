package com.example.springintroduction.controller;

import com.example.springintroduction.member.Member;
import com.example.springintroduction.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/formembers")
    public String onlyForMembers(HttpServletRequest request){
        // getSession(true(default)) JsessionID 쿠키값에 맞는 세션이 있으면 그걸 가져오고, 없으면 새로 생성
        // getSession(false) JsessionID 쿠키값에 맞는 세션이 있으면 그걸 가져오고, 없으면 새로 생성
        HttpSession session = request.getSession(false);
        if ( session == null || session.getAttribute("sessionUsername") == null){
            return "redirect:/";
        }
        return "forMembers";
    }

    @PostConstruct
    public void createDummyData(){
        memberService.save(new Member("상남자", "1234"));
        memberService.save(new Member("하남자", "1234"));
    }
}
