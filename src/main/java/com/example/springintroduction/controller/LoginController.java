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
        /** 위 주석과 아래 한 줄의 코드는 같은 효과
         *  signin.html 파일에 있는 th:value="{}" 에서, Model에 담긴 값을 기반으로 html 텍스트를 표시하도록
         *  하고 있기 때문에, 빈 Member객체라도 Model에 담아 보내지 않으면 오류가 발생한다.
         * */

        model.addAttribute(new Member());
        return "signin";
    }


    /**
     * //  @ModelAttribute 적용된 클래스는 getter와 setter를 이용해 사용자 요청값을 바인딩한다.
     * // @ModelAttribute는 자동으로 Model.addAttribute(member)를 실행한다. 명시적으로 Model객체를 이용해 html 화면에 데이터를 넘길 필요 없다.
     * <p>
     * 사실 AllArgsConstructor로 setter를 대신하고, public field로 getter를 대신할 수 있다. 그렇지만 그냥 Getter Setter만 등록해두자.
     */
    // 해당 path의 POST 요청 시 실행
    @PostMapping("/signin")
    public ResponseEntity signIn(@ModelAttribute Member member, HttpServletRequest request) throws URISyntaxException {
        // 로그인 시도한 데이터를 기반으로 비밀번호 검사
        memberService.validate(member);

        // 세션을 생성한다. 응답에 자동으로 JSESSIONID 쿠키를 보낸다. (set-cookie)
        // 쿠키를 응답받은 웹브라우저는 앞으로 모든 요청에 쿠키를 보내서, 내가 "상남자" 사용자인지 "하남자" 사용자인지 알린다.
        HttpSession session = request.getSession();
        // 세션에는 key-value 형식으로 데이터 저장 가능.
        session.setAttribute("sessionUsername", member.getUsername());
        return ResponseEntity.status(HttpStatus.FOUND).location(new URI("/#signin=ok")).build();
    }

// #은 bookmark link로, 의미없음
/**위의 SignIn 메소드의 다소 복잡해보이는 반환값을 아래처럼 처리할 수 있다. 실제로는 똑같이 적용된다.*/
//    @PostMapping("/signin")
//    public String signIn(@ModelAttribute Member member, HttpServletRequest request){
//
//        memberService.validate(member);
//
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
