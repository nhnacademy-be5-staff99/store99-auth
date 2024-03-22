package com.nhnacademy.store99.auth.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CommonListResponse<T> {
    private final CommonHeader header;
    private final List<T> resultList;

    @Builder
    private CommonListResponse(CommonHeader header, List<T> resultList) {
        this.header = header;
        this.resultList = resultList;
    }
}