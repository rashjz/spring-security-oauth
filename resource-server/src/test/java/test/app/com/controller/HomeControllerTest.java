package test.app.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import test.app.com.domain.Courses;
import test.app.com.domain.User;
import test.app.com.repository.UserRepository;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HomeControllerTest {

    private static final Long USER_ID = 1L;
    private static final String EXPECTED_ERROR_MESSAGE = "user not found with given user Id";

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testUserWithGivenIdMustReturnExpectedResponse() throws Exception {
        User expectedUser = expectedUser();
        when(userRepository.getOne(eq(USER_ID))).thenReturn(expectedUser);
        String expectedUserJson = objectMapper.writeValueAsString(expectedUser);

        mockMvc.perform(get("/user/"+USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedUserJson));

        verify(userRepository, times(1)).getOne(eq(USER_ID));
    }

    @Test
    public void testUserWithWrongUserIdMustReturnExpectedMessage() throws Exception {
        when(userRepository.getOne(eq(USER_ID))).thenReturn(null);
        mockMvc.perform(get("/user/"+USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(EXPECTED_ERROR_MESSAGE));
    }

    private User expectedUser() {
        return User.builder()
                .total("total")
                .userName("testUser")
                .courses(Collections.singletonList(Courses.builder()
                        .grade("test")
                        .name("courseName")
                        .build()))
                .build();
    }
} 
