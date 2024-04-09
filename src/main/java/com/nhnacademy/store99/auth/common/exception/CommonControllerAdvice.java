package com.nhnacademy.store99.auth.common.exception;

import com.nhnacademy.store99.auth.common.CommonHeader;
import com.nhnacademy.store99.auth.common.CommonResponse;
import com.nhnacademy.store99.auth.exception.LoginDtoNotParsingException;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<String>> notFoundExceptionHandler(NotFoundException ex) {
        CommonHeader commonHeader =
                CommonHeader.builder().httpStatus(HttpStatus.NOT_FOUND).resultMessage(ex.getMessage()).build();
        CommonResponse<String>
                commonResponse = CommonResponse.<String>builder().header(commonHeader).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<String>> validationExceptionHandler(NotFoundException ex) {
        CommonHeader commonHeader =
                CommonHeader.builder().httpStatus(HttpStatus.BAD_REQUEST).resultMessage(ex.getMessage()).build();
        CommonResponse<String>
                commonResponse = CommonResponse.<String>builder().header(commonHeader).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CommonResponse<String>> authenticationExceptionHandler(AuthenticationException ex) {
        CommonHeader commonHeader =
                CommonHeader.builder().httpStatus(HttpStatus.UNAUTHORIZED).resultMessage(ex.getMessage()).build();
        CommonResponse<String>
                commonResponse = CommonResponse.<String>builder().header(commonHeader).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(commonResponse);
    }

    @ExceptionHandler(value = {LoginDtoNotParsingException.class})
    public ResponseEntity<CommonResponse<String>> jsonParsingExceptionHandler(IOException ex) {
        CommonHeader commonHeader =
                CommonHeader.builder().httpStatus(HttpStatus.BAD_REQUEST).resultMessage(ex.getMessage()).build();
        CommonResponse<String>
                commonResponse = CommonResponse.<String>builder().header(commonHeader).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }

}
