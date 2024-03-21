package com.nhnacademy.store99.auth.common;

import lombok.Builder;
import lombok.Getter;

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