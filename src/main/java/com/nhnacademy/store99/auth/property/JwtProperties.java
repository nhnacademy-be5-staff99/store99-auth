package com.nhnacademy.store99.auth.property;

import com.nhnacademy.store99.auth.util.SecureKeyManagerUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ahyeon Song
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
public class JwtProperties {
    private final SecureKeyManagerUtil secureKeyManagerUtil;

    private String secret;

    public void setSecret(final String secret) {
        if (secureKeyManagerUtil.isEncrypted(secret)) {
            this.secret = secureKeyManagerUtil.loadConfidentialData(secret);
        } else {
            this.secret = secret;
        }
    }
}
