package com.nhnacademy.store99.auth.common;

import lombok.Builder;
import lombok.Getter;

/**
 * 단 건 공통 응답 객체
 *
 * <p>헤더와 결과를 포함하는 응답 객체
 *
 * @param <T> 결과 객체 타입
 */
@Getter
public class CommonResponse<T> {
    private final CommonHeader header;
    private final T result;

    @Builder
    private CommonResponse(CommonHeader header, T result) {
        this.header = header;
        this.result = result;
    }
}