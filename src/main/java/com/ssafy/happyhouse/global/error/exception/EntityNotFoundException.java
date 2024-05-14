package com.ssafy.happyhouse.global.error.exception;

import com.ssafy.happyhouse.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException{

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
