package com.nhnacademy.store99.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.store99.auth.common.CommonHeader;
import com.nhnacademy.store99.auth.common.CommonResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 로그아웃 성공 시 로직
 *
 * @author Ahyeon Song
 */
@Component
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper;

    public CustomLogoutSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        CommonHeader commonHeader =
                CommonHeader.builder().httpStatus(HttpStatus.OK).resultMessage("Logout Success").build();
        CommonResponse<Void> commonResponse =
                CommonResponse.<Void>builder().header(commonHeader).build();

        String jsonResponse = objectMapper.writeValueAsString(commonResponse);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);

        log.debug("로그아웃 성공 응답 완료");
    }

}
