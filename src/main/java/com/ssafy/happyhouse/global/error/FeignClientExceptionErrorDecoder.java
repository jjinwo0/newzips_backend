package com.ssafy.happyhouse.global.error;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        log.error("{} 요청 실패", methodKey);
        log.error("status: {}", response.status());
        log.error("reason: {}", response.reason());

        FeignException exception = FeignException.errorStatus(methodKey, response);
        HttpStatus status = HttpStatus.valueOf(response.status());

        if (status.is5xxServerError()){ // 500에러 발생 시 재시도
            return new RetryableException(
                    response.status(), // int status
                    exception.getMessage(), // String message
                    response.request().httpMethod(), // HttpMethod httpMethod
                    exception,
                    (Long) null,
                    response.request()
            );
        }
        return errorDecoder.decode(methodKey, response);
    }
}
