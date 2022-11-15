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
