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

@Slf4j
public class FilterExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();


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
