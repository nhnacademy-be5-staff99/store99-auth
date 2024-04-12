package com.nhnacademy.store99.auth.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * OpenFeign 설정
 * <p>Main 에 @EnableFeignClients 를 작성하면 성능이슈가 발생할 가능성 있음.
 * 이를 방지하기 위해 Config 파일을 따로 작성
 * <p>OpenFeign 은 adapter 패키지 아래에 있어야 함.
 *
 * @author Ahyeon Song
 */
@Configuration
@EnableFeignClients("com.nhnacademy.store99.auth.adapter")
public class OpenFeignConfig {

}
