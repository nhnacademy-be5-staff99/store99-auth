package com.nhnacademy.store99.auth.common;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * 리스트 공통 응답 객체
 *
 * <p>헤더와 결과를 포함하는 응답 객체
 *
 * @param <T>
 * @author seunggyu-kim
 */
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