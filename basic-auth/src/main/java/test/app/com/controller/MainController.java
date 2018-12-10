package test.app.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import test.app.com.config.properties.TokenBodyProperties;
import test.app.com.domain.TokenParams;
import test.app.com.util.HttpUtils;

import static org.springframework.security.oauth2.provider.token.AccessTokenConverter.GRANT_TYPE;
import static org.springframework.security.oauth2.provider.token.AccessTokenConverter.SCOPE;

@Slf4j
@RestController
public class MainController {

    private final RestTemplate restTemplate;

    private final TokenBodyProperties properties;

    @Autowired
    public MainController(RestTemplate restTemplate, TokenBodyProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @GetMapping(value = "user")
    public String getResource() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(GRANT_TYPE, properties.getGrantType());
        params.add(SCOPE, properties.getScope());
        params.add("username", properties.getUsername());
        params.add("password", properties.getPassword());

        HttpHeaders headers = HttpUtils.createHeaders(properties.getClientId(), properties.getSecret());

        ResponseEntity<TokenParams> model = restTemplate.exchange(
                properties.getEndpoint(),
                HttpMethod.POST,
                new HttpEntity<Object>(params, headers),
                TokenParams.class);


        return model.getBody().getAccess_token();
    }

} 
