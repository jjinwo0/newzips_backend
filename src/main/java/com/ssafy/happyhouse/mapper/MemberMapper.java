package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.global.token.JwtTokenDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
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

    List<Member> findByUsername(String username);

    Member findMemberByRefreshToken(String refreshToken);

    void updateToken(@Param("id") Long id, @Param("refreshToken") String refreshToken, @Param("refreshTokenExpireTime") LocalDateTime refreshTokenExpireTime);

    void expireToken(Map<String, Object> map);
}

