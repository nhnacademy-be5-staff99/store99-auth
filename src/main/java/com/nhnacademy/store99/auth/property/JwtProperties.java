package com.nhnacademy.store99.auth.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ahyeon Song
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "store99.jwt")
@RequiredArgsConstructor
public class JwtProperties {
    private String secret;
}
