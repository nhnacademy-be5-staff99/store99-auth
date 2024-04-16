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

/**
 * auth server 에서 공통 에러 처리에 사용되는 controller advice
 *
 * @author Ahyeon Song
 */
@RestControllerAdvice
public class CommonControllerAdvice {

    /**
     * NotFoundException Handler
     *
     * @param ex NotFoundException
     * @return 404 NOT_FOUND
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse<String>> notFoundExceptionHandler(NotFoundException ex) {
        CommonHeader commonHeader =
                CommonHeader.builder().httpStatus(HttpStatus.NOT_FOUND).resultMessage(ex.getMessage()).build();
        CommonResponse<String>
                commonResponse = CommonResponse.<String>builder().header(commonHeader).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
    }

    /**
     * ValidationException Handler
     *
     * <p>email, not null 등 valid 를 만족시키지 못할 시 처리
     *
     * @param ex MethodArgumentNotValidException
     * @return 400 BAD_REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<String>> validationExceptionHandler(MethodArgumentNotValidException ex) {
        CommonHeader commonHeader =
                CommonHeader.builder().httpStatus(HttpStatus.BAD_REQUEST).resultMessage(ex.getMessage()).build();
        CommonResponse<String>
                commonResponse = CommonResponse.<String>builder().header(commonHeader).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }

    /**
     * AuthenticationException Handler
     *
     * <p>spring security 의 인증 과정을 만족시키지 못할 시 처리
     *
     * @param ex AuthenticationException
     * @return 401 UNAUTHORIZED
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CommonResponse<String>> authenticationExceptionHandler(AuthenticationException ex) {
        CommonHeader commonHeader =
                CommonHeader.builder().httpStatus(HttpStatus.UNAUTHORIZED).resultMessage(ex.getMessage()).build();
        CommonResponse<String>
                commonResponse = CommonResponse.<String>builder().header(commonHeader).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(commonResponse);
    }

    /**
     * ParsingException Handler
     *
     * <p>Dto 를 json 으로 파싱할 수 없을 시 처리
     *
     * @param ex IOException
     * @return 400 BAD_REQUEST
     */
    @ExceptionHandler(value = {LoginDtoNotParsingException.class})
    public ResponseEntity<CommonResponse<String>> jsonParsingExceptionHandler(IOException ex) {
        CommonHeader commonHeader =
                CommonHeader.builder().httpStatus(HttpStatus.BAD_REQUEST).resultMessage(ex.getMessage()).build();
        CommonResponse<String>
                commonResponse = CommonResponse.<String>builder().header(commonHeader).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }

    /**
     * /v1/auth/login 요청이 POST 가 아닌 다른 메소드로 왔을 경우 처리
     *
     * @param ex - LoginRequestNotPermissionException
     * @return 405 METHOD_NOT_ALLOWED
     */
//    @ExceptionHandler(value = {LoginRequestNotPermissionException.class})
//    public ResponseEntity<CommonResponse<Void>> loginMethodNotPermissionExceptionHandler(
//            LoginRequestNotPermissionException ex) {
//        CommonHeader commonHeader =
//                CommonHeader.builder().httpStatus(HttpStatus.METHOD_NOT_ALLOWED).resultMessage(ex.getMessage()).build();
//        CommonResponse<Void> commonResponse = CommonResponse.<Void>builder().header(commonHeader).build();
//        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(commonResponse);
//    }

}
