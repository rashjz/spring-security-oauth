package test.app.com.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final Environment environment;

    @Autowired
    public CustomAuthenticationProvider(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        log.info("CustomAuthenticationProvider invoked with username {} and password {}", username, password);
        if (username.equalsIgnoreCase(environment.getProperty("login")) &&
                password.equalsIgnoreCase(environment.getProperty("password"))) {
            return new UsernamePasswordAuthenticationToken (username, password, Collections.emptyList());
        } else {
            log.debug("CustomAuthenticationProvider error with username {} and password {}", username, password);
            throw new BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}