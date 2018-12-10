package test.app.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
public class RemoteTokenConfiguration {
      @Primary
    @Bean
    public RemoteTokenServices tokenService(Environment environment) {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(environment.getProperty("oauth.token.endpoint"));
        tokenService.setClientId(environment.getProperty("oauth.token.client-id"));
        tokenService.setClientSecret(environment.getProperty("oauth.token.secret"));
        return tokenService;
    }


} 
