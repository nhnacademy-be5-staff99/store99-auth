package com.nhnacademy.store99.auth.common;

import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonHeader {
    private final boolean isSuccessful;
    private final HttpStatus httpStatus;
    private final String resultMessage;     // Success or Error message

    @Builder
    private CommonHeader(HttpStatus httpStatus, String resultMessage) {
        httpStatus = Objects.isNull(httpStatus) ? HttpStatus.OK : httpStatus;
        resultMessage = Objects.isNull(resultMessage) ? httpStatus.getReasonPhrase() : resultMessage;

        this.isSuccessful = httpStatus.is2xxSuccessful();
        this.httpStatus = httpStatus;
        this.resultMessage = resultMessage;
    }
}
