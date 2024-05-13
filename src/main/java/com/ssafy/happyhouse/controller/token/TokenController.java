package com.ssafy.happyhouse.controller.token;

import com.ssafy.happyhouse.dto.token.AccessTokenResponseDto;
import com.ssafy.happyhouse.global.token.GrantType;
import com.ssafy.happyhouse.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/access-token/issue")
    public ResponseEntity<AccessTokenResponseDto> createAccessToken(HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");

        String[] authorizations = authorization.split(" ");

        if (authorizations.length<2 || (!GrantType.BEARER.getType().equals(authorizations[0])))
            throw new RuntimeException("Token Authentication Exception"); //TODO: Exception 정의 수정

        String refreshToken = authorization.split(" ")[1];

        AccessTokenResponseDto accessTokenResponseDTO = tokenService.createAccessTokenByRefreshToken(refreshToken);

        return ResponseEntity.ok(accessTokenResponseDTO);
    }
}