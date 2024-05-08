package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.entity.Member;
import com.ssafy.happyhouse.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public Member findByUsernameAndPassword(String username, String password){

        Map<String, String> map = new HashMap<>();

        map.put("username", username);
        map.put("password", password);

        Member findMember = memberMapper.findByUsernameAndPassword(map);

        if (findMember == null)
            throw new RuntimeException("Not Found Member");

        return findMember;
    }

    public Member findById(Long id){

        Member findMember = memberMapper.findById(id);

        if (findMember == null)
            throw new RuntimeException("Not Found Member");

        return findMember;
    }
}
