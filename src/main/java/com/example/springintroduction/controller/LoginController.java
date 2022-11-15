package com.example.springintroduction.controller;

import com.example.springintroduction.member.Member;
import com.example.springintroduction.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/signin")
    public String signInPage(Model model){
//        Member member = new Member();
//        model.addAttribute("member", member);
        model.addAttribute(new Member());
        return "signin";
    }

    @PostMapping("/signin")
    // @ModelAttribute는 자동으로 Model.addAttribute(member) 실행한다.
    public String signIn(@ModelAttribute Member member, HttpServletRequest request){
        memberService.validate(member);
        HttpSession session = request.getSession();
        session.setAttribute("sessionUsername", member.getUsername());
        return "redirect:/?signin=ok";
    }

    @GetMapping("/signup")
    public String signUpPage(Model model){
        model.addAttribute(new Member());
        return "signup";
    }

    @PostMapping("/signup")
    // @ModelAttribute는 자동으로 Model.addAttribute(member) 실행한다.
    public String signUp(@ModelAttribute @Valid Member member, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "signup";
        }

        memberService.save(member);
        return "redirect:/?signup=ok";
    }

//    @PostMapping("/signin")
//    public ResponseEntity signin(@ModelAttribute Member member, Model model) throws URISyntaxException {
//        memberService.validate(member);
//        return ResponseEntity.status(HttpStatus.FOUND).location(new URI("/?success=true")).build();
//    }

    @PostMapping("/signout")
    public String signOut(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/";
    }
}
