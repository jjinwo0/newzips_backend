package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.member.MemberDto;
import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.entity.member.constant.MemberType;
import com.ssafy.happyhouse.entity.member.constant.Role;
import com.ssafy.happyhouse.global.error.ErrorCode;
import com.ssafy.happyhouse.global.error.exception.BusinessException;
import com.ssafy.happyhouse.global.error.exception.EntityNotFoundException;
import com.ssafy.happyhouse.global.token.JwtTokenDto;
import com.ssafy.happyhouse.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public void validUsername(String username){

        Member findByUsername = memberMapper.findByUsername(username);

        if (findByUsername != null)
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_USERNAME);
    }

    public void validEmail(String email){

        Member findByEmail = memberMapper.findByEmail(email);

        if (findByEmail != null)
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_USERNAME);
    }

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

        return findMember;
    }

    public Member findByEmail(String email){

        Member findMember = memberMapper.findByEmail(email);

//        if (findMember == null)
//            throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS);

        return findMember;
    }

    public List<Member> findExpertList() {

        return memberMapper.findExpertList();
    }

    @Transactional
    public void join(MemberDto.Join dto){

        validUsername(dto.getUsername());
        validEmail(dto.getEmail());

        Member joinMember = Member.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .role(Role.USER)
                .memberType(MemberType.LOCAL)
                .nickname(dto.getNickname())
                .profile("https://api.dicebear.com/8.x/pixel-art/svg?seed="+dto.getUsername())
                .build();

        memberMapper.join(joinMember);
    }

    @Transactional
    public void joinByEntity(Member member){

        log.info("Join Member Username : {}", member.getUsername());
        log.info("Join Member Email : {}", member.getEmail());
        log.info("Join Member Password : {}", member.getPassword());
        log.info("Join Member Role : {}", member.getRole());
        log.info("Join Member Type : {}", member.getMemberType());
        log.info("Join Member NickName : {}", member.getNickname());

        memberMapper.join(member);
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

        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("refreshToken", refreshToken);
        map.put("refreshTokenExpireTime", refreshTokenExpireTime);

        memberMapper.updateToken(map);
    }

    @Transactional
    public void expireToken(Long id, LocalDateTime now) {

        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("now", now);

        memberMapper.expireToken(map);
    }
}
