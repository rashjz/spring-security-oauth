package test.app.com.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oauth.token")
public class TokenBodyProperties {
    private String username;
    private String password;
    private String secret;
    private String clientId;
    private String endpoint;
    private String scope;
    private String grantType;

} 
