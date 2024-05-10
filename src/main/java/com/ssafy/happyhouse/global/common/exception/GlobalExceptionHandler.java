package com.ssafy.happyhouse.global.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception e) {

        log.error("Exception :: " + e);
        log.error("Exception Message :: " + e.getMessage());
        log.error("Caused :: " + e.getCause());

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
