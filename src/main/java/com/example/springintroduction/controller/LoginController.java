package com.example.springintroduction.controller;

import com.example.springintroduction.member.Member;
import com.example.springintroduction.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

// 회원가입과 인증을 맡은 컨트롤러
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    // 해당 path의 Get 요청 시 실행
    @GetMapping("/signin")
    public String signInPage(Model model) {
//        Member member = new Member();
//        model.addAttribute("member", member);
        /** 위 주석과 아래 한 줄의 코드는 같은 효과 */
        model.addAttribute(new Member());
        return "signin";
    }

    // 해당 path의 POST 요청 시 실행

    /**
     * // @ModelAttribute는 자동으로 Model.addAttribute(member) 실행한다.
     * //  @ModelAttribute는 getter와 setter를 이용해 사용자 요청값을 바인딩한다.
     * <p>
     * 사실 AllArgsConstructor로 setter를 대신하고, public field로 getter를 대신할 수 있다.
     */
    @PostMapping("/signin")
    public ResponseEntity signin(@ModelAttribute Member member, HttpServletRequest request) throws URISyntaxException {
        // 로그인 시도한 데이터를 기반으로 비밀번호 검사
        memberService.validate(member);

        // 세션을 생성한다. 응답에 자동으로 JSESSIONID 쿠키를 보낸다.
        HttpSession session = request.getSession();
        session.setAttribute("sessionUsername", member.getUsername());
        return ResponseEntity.status(HttpStatus.FOUND).location(new URI("/#signin=ok")).build();
    }

// #은 bookmark link로, 의미없음
//    위와 같다.
//    @PostMapping("/signin")
//    public String signIn(@ModelAttribute Member member, HttpServletRequest request){
//
//        // 로그인 시도한 데이터를 기반으로 비밀번호 검사
//        memberService.validate(member);
//
//        // 세션을 생성한다. 응답에 자동으로 JSESSIONID 쿠키를 보낸다.
//        HttpSession session = request.getSession();
//        session.setAttribute("sessionUsername", member.getUsername());
//        return "redirect:/#signin=ok";
//    }


    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute(new Member());
        return "signup";
    }

    @PostMapping("/signup")
    // @ModelAttribute는 자동으로 Model.addAttribute(member) 실행한다.
    // 클래스에 @Valid 어노테이션 적용시키고, 필드에 제약조건 달아 검증하기.
    public String signUp(@ModelAttribute @Valid Member member, BindingResult bindingResult) {
        /**
         // Member 객체 데이터 바인딩에 문제가 생길 경우 되돌아감.
         // 바인딩에 실패한 member 객체를 model에 다시 담아서 signUp 페이지를 보여주기 때문에,
         사용자 입력값 검증에 실패하더라도 사용자가 입력한 데이터가 그대로 남아있다.
         */
        if (bindingResult.hasErrors()) {
            return "signup";
        }


        memberService.save(member);
        return "redirect:/#signup=ok";
    }


    @PostMapping("/signout")
    public String signOut(HttpServletRequest request) {
        // 세션 만료
        request.getSession().invalidate();
        return "redirect:/";
    }
}
