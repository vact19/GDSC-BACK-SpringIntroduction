package com.example.springintroduction.member;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemberRepository {

    public static Map<String, Member> memberMap = new HashMap<>();

    public void save(Member member){
        memberMap.put(member.getUsername(), member);
    }

    public List<Member> findAll() {
        Collection<Member> values =  memberMap.values();
        return new ArrayList<>(values);
    }
}
