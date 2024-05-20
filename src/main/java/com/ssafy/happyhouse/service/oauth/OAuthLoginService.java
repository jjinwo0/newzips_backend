package com.ssafy.happyhouse.service.oauth;

import com.ssafy.happyhouse.dto.oauth.OAuthAttributes;
import com.ssafy.happyhouse.dto.oauth.OAuthDto;
import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.entity.member.constant.MemberType;
import com.ssafy.happyhouse.entity.member.constant.Role;
import com.ssafy.happyhouse.global.error.ErrorCode;
import com.ssafy.happyhouse.global.error.exception.AuthenticationException;
import com.ssafy.happyhouse.global.token.JwtTokenDto;
import com.ssafy.happyhouse.global.token.TokenManager;
import com.ssafy.happyhouse.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OAuthLoginService {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    @Transactional
    public OAuthDto.Response oauthLogin(OAuthDto.Request dto, String accessToken, MemberType memberType) {

        log.info("memberType check: {}", memberType.getMemberType());

        SocialLoginApiService socialLoginService = SocialLoginApiServiceFactory.getSocialLoginService(memberType);

        OAuthAttributes userInfo = socialLoginService.getUserInfo(accessToken);

        log.info("userInfo : {}", userInfo);
        log.info("accessToken : {}", accessToken);
        log.info("accessToken : {}", dto.getAccessToken());
        log.info("expireTime : {}", dto.getAccessTokenExpireTime());

        JwtTokenDto tokenDto;

        Member findMember = memberService.findById(userInfo.getId());

        // 신규 회원
        if (findMember == null) {

            Member oauthMember = userInfo.toMemberEntity(memberType, Role.USER);

            memberService.joinByEntity(oauthMember);

            tokenDto = tokenManager.createJwtTokenDtoByOauth(
                    oauthMember.getId(),
                    oauthMember.getUsername(),
                    oauthMember.getRole(),
                    oauthMember.getNickname(),
                    oauthMember.getMemberType().getMemberType(),
                    oauthMember.getProfile(),
                    dto.getAccessToken(),
                    dto.getAccessTokenExpireTime(),
                    dto.getRefreshToken(),
                    dto.getRefreshTokenExpireTime()
            );

            memberService.updateToken(oauthMember.getId(), tokenDto);
        } else { // 기존 회원

            tokenDto = tokenManager.createJwtTokenDtoByOauth(
                    findMember.getId(),
                    findMember.getUsername(),
                    findMember.getRole(),
                    findMember.getNickname(),
                    findMember.getMemberType().getMemberType(),
                    findMember.getProfile(),
                    dto.getAccessToken(),
                    dto.getAccessTokenExpireTime(),
                    dto.getRefreshToken(),
                    dto.getRefreshTokenExpireTime()
            );

            memberService.updateToken(findMember.getId(), tokenDto);
        }

        return OAuthDto.Response.of(tokenDto);
    }

    @Transactional
    public Long oauthLogout(String accessToken, MemberType memberType) {

        SocialLoginApiService socialLoginService = SocialLoginApiServiceFactory.getSocialLoginService(memberType);

        Long logoutId = socialLoginService.logoutUser(accessToken);

        if (logoutId == null)
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);

        return logoutId;
    }
}
