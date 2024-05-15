package com.ssafy.happyhouse.service.oauth;

import com.ssafy.happyhouse.entity.member.constant.MemberType;
import com.ssafy.happyhouse.global.error.ErrorCode;
import com.ssafy.happyhouse.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class OAuthValidator {

    public void validateMemberType(String memberType){
        if (!MemberType.isMemberType(memberType))
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
    }
}
