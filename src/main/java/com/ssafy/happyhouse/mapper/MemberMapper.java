package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.member.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {

    List<Member> findAll();

    Member findById(Long id);

    Member findByUsernameAndPassword(Map<String, String> map);

    void join(Member member);

    void update(Map<String, Object> map);

    void delete(Long id);
}

