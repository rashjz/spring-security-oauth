package test.app.com.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import test.app.com.entities.Role;
import test.app.com.entities.User;
import test.app.com.repositories.UserRepository;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test test cases of {@link UserService}
 **/
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository repo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;
    @Captor
    private ArgumentCaptor<User> argumentCaptor;

    @Test
    public void testSaveMethodOn() {
        String encodedParam = "encodedPassword";
        User expectedUser = expectedUser();

        when(passwordEncoder.encode(eq(expectedUser.getPassword())))
                .thenReturn(encodedParam);
        userService.save(expectedUser);

        expectedUser.setPassword(encodedParam);
        //verify and capture saved user
        verify(repo, times(1)).save(argumentCaptor.capture());

        User actualUser = argumentCaptor.getValue();
        Assert.assertEquals(expectedUser,actualUser);
    }

    private User expectedUser() {
        return new User(
                "test-user",
                "test-password",
                Collections.singletonList(new Role("USER")),
                true
        );
    }
} 
