package test.app.com.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import test.app.com.entities.Role;
import test.app.com.entities.User;
import test.app.com.repositories.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by Rashad on 12/11/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testLoadUserByUsernameCheckResult() {
        User user = expectedUser();
        when(repo.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
        assertNotNull(userDetails);
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
