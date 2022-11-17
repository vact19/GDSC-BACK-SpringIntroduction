package com.example.springintroduction.controller;

import com.example.springintroduction.member.Member;
import com.example.springintroduction.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

// 메인화면의 자잘한 기능들을 수행하는 컨트롤러

/** 0. Member 생성
 *  1. PostConstruct 더미데이터 생성
 *  2. 메인페이지 가지는지
 *  3. [Get /] 에 조회기능 넣기
 *  4. 회원가입 검증없이 (ModelAtttribute)
 *      302, setcookie httponly
 *  5. 회원가입 검증넣기
 *  6. 로그인하기 (세션까지 한번에)
 *  7. 로그인유지를 확인하기 위해 formembers
 *  8. 로그아웃
 */
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;
    // 스프링은 아래처럼 실체(객체)를 넣는 수고를 덜어 준다.
    //private final MemberService memberService = new MemberService(new MemberRepository(), new BCryptPasswordEncoder());

    /**
     *  RequiredArgsConstructor는 final 필드를 파라미터로 받는 생성자를 만든다.
     *  RequiredArgsConstructor + final 필드의 조합은 아래 주석처리한 생성자를 만들고, @Autowired까지 자동 생성되어
     *  생성자 기반의 의존성 주입이 가능해지게 한다.
     */

    /** @Autowired : 해당 메소드 혹은 생성자의 파라미터가 스프링에서 관리하는 클래스 (스프링 빈으로 등록된 클래스) 일 경우, 의존성 주입을 실행함.*/
    /** 생성자가 하나일 경우, @Autowired를 명시하지 않아도 자동 적용 */
    // 생성자
//    public MainController(MemberService memberService) {
//        this.memberService = memberService;
//    }


    /**
     * @RequestParam
     *  in Spring MVC, "request parameters" map to query parameters, form data, and parts in multipart requests
     *  쿼리 파라미터와 폼 데이터를 받는다.
     *
     *  예시에서는 url에 적힌 쿼리 스트링으로 값을 전달했지만, html form 태그를 이용한 값 전달도 가능하다.
     * */
    // 해당 path의 Get 요청 시 실행
    @GetMapping("/")
    public String mainPage(Model model, @RequestParam(required = false) String query){

        if( query == null ){
            List<Member> members = memberService.findAll();
            model.addAttribute("members", members);

            //model.addAttribute(members); // attributeName memberList
            return "home"; // templates 디렉토리에서 home.html을 찾는다.
        }

        List<Member> members = memberService.findByUsername(query);
        model.addAttribute("members", members);
        return "home";
    }

    @GetMapping("/formembers")
    public String forMembers(HttpServletRequest request){

        // getSession(true(default)) JsessionID 쿠키값에 맞는 세션이 있으면 그걸 가져오고, 없으면 새로 생성
        // getSession(false) JsessionID 쿠키값에 맞는 세션이 있으면 그걸 가져오고, 없으면 null 반환
        HttpSession session = request.getSession(false);

        // 웹브라우저가 보낸 요청에 담긴 쿠키에 알맞은 세션ID가 담겨서 왔다면, session에는 해당 세션ID의 세션 객체가 들어있다.
        // 쿠키에 알맞은 세션ID가 담겨서 오지 않았다면, session은 null 상태이다.
        if ( session == null || session.getAttribute("sessionUsername") == null){
            // session null -> 비로그인 사용자이므로 메인화면 돌려보냄
            return "redirect:/";
        }

        return "formembers";
    }

    // 스프링 애플리케이션이 실행되고, 의존성 주입 작업이 끝난 후 실행됨
    @PostConstruct
    public void createDummyData(){
        memberService.save(new Member("상남자", "1234"));
        memberService.save(new Member("하남자", "1234"));
    }
}
