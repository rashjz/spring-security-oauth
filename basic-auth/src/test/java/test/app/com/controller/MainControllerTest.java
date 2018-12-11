package test.app.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import test.app.com.config.properties.TokenBodyProperties;
import test.app.com.domain.Courses;
import test.app.com.domain.TokenParams;
import test.app.com.domain.User;
import test.app.com.util.HttpUtils;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.oauth2.common.OAuth2AccessToken.ACCESS_TOKEN;
import static org.springframework.security.oauth2.provider.token.AccessTokenConverter.GRANT_TYPE;
import static org.springframework.security.oauth2.provider.token.AccessTokenConverter.SCOPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MainControllerTest test cases of {@link MainController}
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MainControllerTest {
    private static final String RESOURCE_URL = "http://localhost:8082/resource-server/user/";


    private static final String USER_ID = "1";

    @MockBean
    private TokenBodyProperties properties;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    private TokenParams tokenParams;
    private UriComponentsBuilder builder;

    @Before
    public void init() {
        when(properties.getClientId()).thenReturn("test-id");
        when(properties.getEndpoint()).thenReturn("http://test-client.com/");
        when(properties.getGrantType()).thenReturn("test-client");
        when(properties.getScope()).thenReturn("test-client");
        when(properties.getSecret()).thenReturn("test-client");
        when(properties.getUsername()).thenReturn("test-client");
        when(properties.getPassword()).thenReturn("test-client");

        builder = UriComponentsBuilder.fromHttpUrl(properties.getEndpoint())
                .queryParam(GRANT_TYPE, properties.getGrantType())
                .queryParam(SCOPE, properties.getScope())
                .queryParam("username", properties.getUsername())
                .queryParam("password", properties.getPassword());

        tokenParams = TokenParams.builder()
                .access_token("test-access-token-param")
                .expires_in(232423)
                .scope("scope")
                .token_type("token")
                .build();
    }


    @Test
    public void test() throws Exception {
        //mock rest template for authentication server post call
        when(restTemplate.postForObject(eq(builder.toUriString()), any(), any()))
                .thenReturn(tokenParams);

        //mock rest template for resource server call
        UriComponentsBuilder resourceApiUriBuilder = UriComponentsBuilder.fromHttpUrl(RESOURCE_URL + USER_ID)
                .queryParam(ACCESS_TOKEN, tokenParams.getAccess_token());
        User expectedUser = expectedUser();
        when(restTemplate.getForObject(eq(resourceApiUriBuilder.toUriString()), eq(User.class)))
                .thenReturn(expectedUser);

        mockMvc.perform(get("/user/" + USER_ID)
                .headers(HttpUtils.createHeaders("data-admin", "admin1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedUser)));


        verify(restTemplate, atLeastOnce()).postForObject(
                eq(builder.toUriString()),
                any(),
                any());

    }

    private User expectedUser() {
        return User.builder()
                .total("total")
                .userName("testUser")
                .courses(Collections.singletonList(Courses.builder()
                        .grade("testGrade")
                        .name("courseName")
                        .build()))
                .build();
    }

}
