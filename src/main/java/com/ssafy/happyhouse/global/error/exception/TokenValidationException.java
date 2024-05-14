package com.ssafy.happyhouse.global.error.exception;

import com.ssafy.happyhouse.global.error.ErrorCode;

public class TokenValidationException extends BusinessException{

    public TokenValidationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
