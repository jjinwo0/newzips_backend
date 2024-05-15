package com.ssafy.happyhouse.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TEST(HttpStatus.OK, "테스트입니다."),

    // 인증 & 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "부적절한 토큰 유형입니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "Authorization Header가 빈 값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "해당 refresh token은 만료되었습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "해당 토큰은 access token이 아닙니다."),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "관리자 Role이 아닙니다."),
    NOT_EQUAL_PASSWORD(HttpStatus.UNAUTHORIZED, "Password가 일치하지 않습니다."),
    NOT_EQUAL_CODE(HttpStatus.UNAUTHORIZED, "Email Code가 일치하지 않습니다."),
    NOT_GRANTED_MEMBER(HttpStatus.FORBIDDEN, "해당 회원에게 접근 권한이 없습니다."),

    // 회원
    INVALID_MEMBER_TYPE(HttpStatus.FORBIDDEN, "잘못된 회원 타입입니다. (memberType : KAKAO)"),
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    MEMBER_NOT_EXISTS(HttpStatus.NOT_FOUND, "해당 회원은 존재하지 않습니다."),
    NOT_EXISTS_PASSWORD(HttpStatus.NOT_FOUND, "Password가 일치하지 않습니다."),
    ALREADY_REGISTERED_USERNAME(HttpStatus.BAD_REQUEST, "이미 가입된 아이디입니다."),
    ALREADY_REGISTERED_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 Email 입니다."),

    // 주택
    NOT_EXISTS_HOUSE(HttpStatus.NOT_FOUND, "조건에 맞는 주택 정보가 없습니다."),

    // 카카오맵
    NO_MARKER(HttpStatus.NO_CONTENT, "마커가 존재하지 않습니다.")
    ;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = httpStatus.value();
        this.errorPhrase = httpStatus.getReasonPhrase();
        this.message = message;
    }

    private HttpStatus httpStatus;
    private Integer errorCode;
    private String errorPhrase;
    private String message;
}
