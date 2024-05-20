package com.ssafy.happyhouse.controller.member;

import com.ssafy.happyhouse.dto.oauth.OAuthDto;
import com.ssafy.happyhouse.entity.member.constant.MemberType;
import com.ssafy.happyhouse.global.error.ErrorCode;
import com.ssafy.happyhouse.global.error.exception.AuthenticationException;
import com.ssafy.happyhouse.global.token.GrantType;
import com.ssafy.happyhouse.service.oauth.OAuthLoginService;
import com.ssafy.happyhouse.service.oauth.OAuthValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member/oauth")
@RequiredArgsConstructor
public class OAuthLoginController {

    private final OAuthValidator validator;

    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/kakao/login")
    public ResponseEntity<OAuthDto.Response> oauthLogin(@RequestBody OAuthDto.Request dto,
                                               HttpServletRequest httpServletRequest) {

        String authorization = httpServletRequest.getHeader("Authorization");

        if (!StringUtils.hasText(authorization))
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);

        String[] splitHeader = authorization.split(" ");

        if (splitHeader.length < 2 || (!GrantType.BEARER.getType().equals(splitHeader[0])))
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);

        validator.validateMemberType(dto.getMemberType());

        String accessToken = authorization.split(" ")[1];

        OAuthDto.Response token = oAuthLoginService.oauthLogin(accessToken, MemberType.from(dto.getMemberType()));

        return ResponseEntity.ok(token);
    }

    @PostMapping("/kakao/logout")
    public ResponseEntity<?> oauthLogout(HttpServletRequest httpServletRequest) {

        String authorization = httpServletRequest.getHeader("Authorization");

        if (!StringUtils.hasText(authorization))
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);

        String[] splitHeader = authorization.split(" ");

        if (splitHeader.length < 2 || (!GrantType.BEARER.getType().equals(splitHeader[0])))
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);

        String accessToken = authorization.split(" ")[1];

        Long logoutId = oAuthLoginService.oauthLogout(accessToken, MemberType.KAKAO);

        return ResponseEntity.ok(logoutId);
    }
}
