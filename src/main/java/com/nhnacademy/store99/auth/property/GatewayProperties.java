package com.nhnacademy.store99.auth.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "store99.gateway.api.bookstore")
@RequiredArgsConstructor
public class GatewayProperties {
    private String url;
}
