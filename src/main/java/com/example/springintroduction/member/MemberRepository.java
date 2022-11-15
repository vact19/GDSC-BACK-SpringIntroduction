package com.example.springintroduction.member;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemberRepository {

    public static Map<String, Member> memberMap = new HashMap<>();

    // Map에 <멤버명, 멤버객체> 로 저장한다.
    public void save(Member member){
        memberMap.put(member.getUsername(), member);
    }

    // 현재 가입된 멤버를 모두 조회
    public List<Member> findAll() {
        Collection<Member> values =  memberMap.values();
        return new ArrayList<>(values);
    }

    // 이름으로 찾기
    public List<Member> findByUsername(String username) {
        return List.of(memberMap.get(username));
    }
}
