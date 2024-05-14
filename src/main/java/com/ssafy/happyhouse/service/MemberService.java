package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.member.MemberDto;
import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.entity.member.constant.Role;
import com.ssafy.happyhouse.global.error.ErrorCode;
import com.ssafy.happyhouse.global.error.exception.EntityNotFoundException;
import com.ssafy.happyhouse.global.token.JwtTokenDto;
import com.ssafy.happyhouse.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public List<Member> findAll() {

        List<Member> findAll = memberMapper.findAll();

        if (findAll.isEmpty())
            throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS);

        return findAll;
    }

    public Member findByUsernameAndPassword(String username, String password){

        Map<String, String> map = new HashMap<>();

        map.put("username", username);
        map.put("password", password);

        Member findMember = memberMapper.findByUsernameAndPassword(map);

        if (findMember == null)
            throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS);

        return findMember;
    }

    public Member findById(Long id){

        Member findMember = memberMapper.findById(id);

        if (findMember == null)
            throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS);

        return findMember;
    }

    @Transactional
    public void join(MemberDto.Join dto){

        Member joinMember = Member.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(Role.USER)
                .build();

        memberMapper.join(joinMember);
    }

    @Transactional
    public void updateMember(MemberDto.Update dto){

        Map<String, Object> updateMap = new HashMap<>();

        updateMap.put("username", dto.getUsername());
        updateMap.put("password", dto.getPassword());
        updateMap.put("email", dto.getEmail());

        memberMapper.update(updateMap);
    }

    @Transactional
    public void deleteMember(Long id){

        memberMapper.delete(id);
    }

    public Boolean findByUsername(String username){

        List<Member> findList = memberMapper.findByUsername(username);

        if (findList.isEmpty())
            return true;

        return false;
    }

    public Member findMemberByRefreshToken(String refreshToken){

        Member findMember = memberMapper.findMemberByRefreshToken(refreshToken);

        if (findMember == null)
            throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS);

        return findMember;
    }

    @Transactional
    public void updateToken(Long id, JwtTokenDto token){

        String refreshToken = token.getRefreshToken();
        LocalDateTime refreshTokenExpireTime = token.getRefreshTokenExpireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        memberMapper.updateToken(id, refreshToken, refreshTokenExpireTime);
    }

    @Transactional
    public void expireToken(Long id, LocalDateTime now) {

        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("now", now);

        memberMapper.expireToken(map);
    }
}
