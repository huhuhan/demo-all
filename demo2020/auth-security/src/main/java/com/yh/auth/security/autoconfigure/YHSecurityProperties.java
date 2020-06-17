package com.yh.auth.security.autoconfigure;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "yh.security"
)
@Data
@NoArgsConstructor
public class YHSecurityProperties {
    /** xss请求白名单 */
    private String xssIgnores = "";
    /** csrf请求白名单 */
    private String csrfIgnores = "127.0.0.1";
    /** auth请求白名单 */
    private String authIgnores = "/login.*";
}
