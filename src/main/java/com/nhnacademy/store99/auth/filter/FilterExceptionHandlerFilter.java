package com.nhnacademy.store99.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.store99.auth.common.CommonHeader;
import com.nhnacademy.store99.auth.common.CommonResponse;
import com.nhnacademy.store99.auth.exception.LoginRequestNotPermissionException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * CustomAuthenticationFilter 에서 발생하는 예외를 처리하는 filter
 *
 * @author Ahyeon Song
 */
@Slf4j
public class FilterExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * <ol>
     *  <li>LoginRequestNotPermissionException
     *  <p>로그인 요청이 POST 이외의 다른 method 로 오면 405 METHOD_NOT_ALLOWED 반환
     * </ol>
     * <p>에러에 걸리지 않을 시 filter 를 넘어감
     * <p>다른 예외 발생 시, catch 문으로 추가
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (LoginRequestNotPermissionException ex) {
            log.error("filter exception message : {}", ex.getMessage());
            CommonHeader commonHeader =
                    CommonHeader.builder().httpStatus(HttpStatus.METHOD_NOT_ALLOWED).resultMessage(ex.getMessage())
                            .build();
            CommonResponse<Void> commonResponse = CommonResponse.<Void>builder().header(commonHeader).build();

            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            response.setContentType(MediaType.APPLICATION_JSON);
            objectMapper.writeValue(response.getWriter(), commonResponse);
        }
    }
}
