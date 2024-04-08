package com.nhnacademy.store99.auth.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ahyeon Song
 */
@Configuration
@EnableFeignClients("com.nhnacademy.store99.auth.adapter")
public class OpenFeignConfig {

}
