package com.ssafy.happyhouse.global.error.exception.handler;

import com.ssafy.happyhouse.global.error.ErrorResponse;
import com.ssafy.happyhouse.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid 또는 @Validated binding error가 발생할 경우
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {

        log.error("handleBindException :: ", e);

        ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getBindingResult());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        log.error("handleMethodArgumentTypeMismatchException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    /**
     * 지원하지 않는 HTTP Method 호출할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException (HttpRequestMethodNotSupportedException e){
        log.error("handleHttpRequestMethodNotSupportedException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(errorResponse);
    }

    /**
     * 비즈니스 로직 실행 중 오류 발생
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleConflict(BusinessException e){
        log.error("handleConflict", e);
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(errorResponse);
    }

    /**
     * 나머지 예외 발생
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e){
        log.error("Exception", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
