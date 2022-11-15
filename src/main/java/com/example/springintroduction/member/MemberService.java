package com.example.springintroduction.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // Member 객체 저장.
    // 비즈니스 로직은 Service 클래스가 수행, 저장은 Service 클래스가 Repository 클래스에게 맡긴다.
    public void save(Member member){
        String encoded = passwordEncoder.encode(member.getPassword());
        member.setPassword(encoded);
        memberRepository.save(member);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    // 로그인 시도한 회원정보 검증
    public void validate(Member member) {
        Member foundMember = MemberRepository.memberMap.get(member.getUsername());

        if ( !passwordEncoder.matches(member.getPassword(), foundMember.getPassword())){
            throw new IllegalArgumentException("비밀번호 틀림");
        }
    }
}
