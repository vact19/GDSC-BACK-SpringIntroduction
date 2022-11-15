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


@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    // 해당 path의 Get 요청 시 실행
    /**
     *  in Spring MVC, "request parameters" map to query parameters, form data, and parts in multipart requests
     *  쿼리 파라미터와 폼 데이터를 받는다.
     * */
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
        // getSession(false) JsessionID 쿠키값에 맞는 세션이 있으면 그걸 가져오고, 없으면 새로 생성
        HttpSession session = request.getSession(false);

        // 쿠키에 알맞은 세션ID가 담겨서 왔다면, session에는 해당 세션ID의 세션 객체가 들어있다.
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
