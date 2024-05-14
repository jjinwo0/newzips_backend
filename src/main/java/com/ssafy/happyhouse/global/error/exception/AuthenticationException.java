package com.ssafy.happyhouse.global.error.exception;

import com.ssafy.happyhouse.global.error.ErrorCode;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
