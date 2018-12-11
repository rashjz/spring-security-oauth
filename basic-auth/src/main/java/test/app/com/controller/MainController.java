package test.app.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import test.app.com.config.properties.TokenBodyProperties;
import test.app.com.domain.TokenParams;
import test.app.com.domain.User;
import test.app.com.util.HttpUtils;

import static org.springframework.security.oauth2.common.OAuth2AccessToken.ACCESS_TOKEN;
import static org.springframework.security.oauth2.provider.token.AccessTokenConverter.GRANT_TYPE;
import static org.springframework.security.oauth2.provider.token.AccessTokenConverter.SCOPE;

@Slf4j
@RestController
public class MainController {

    private static final String RESOURCE_URL = "http://localhost:8082/resource-server/user/";
    private final RestTemplate restTemplate;

    private final TokenBodyProperties properties;

    @Autowired
    public MainController(RestTemplate restTemplate, TokenBodyProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @GetMapping(value = "/user/{userId}")
    public User getResource(@PathVariable(name = "userId") Long userId) {
        TokenParams params = getTokenParams();
        log.info("Authentication server token params : {} ", params.toString());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(RESOURCE_URL + userId)
                .queryParam(ACCESS_TOKEN, params.getAccess_token());

        return restTemplate.getForObject(builder.toUriString(), User.class);
    }

    private TokenParams getTokenParams() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(properties.getEndpoint())
                .queryParam(GRANT_TYPE, properties.getGrantType())
                .queryParam(SCOPE, properties.getScope())
                .queryParam("username", properties.getUsername())
                .queryParam("password", properties.getPassword());

        HttpHeaders headers = HttpUtils.createHeaders(properties.getClientId(), properties.getSecret());
        TokenParams result = restTemplate.postForObject(builder.toUriString(), new HttpEntity<>(headers), TokenParams.class);
        log.debug("TokenParams from Authentication server {}", result.toString());
        return result;

    }

} 
