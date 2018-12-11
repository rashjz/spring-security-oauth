package test.app.com.services;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import test.app.com.entities.Role;
import test.app.com.entities.User;
import test.app.com.repositories.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testLoadUserByUsernameCheckResultOnSuccess() {
        User user = expectedUser();
        when(repo.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
        assertNotNull(userDetails);
    }

    @Test
    public void testLoadUserByUsernameCheckUsernameNotFoundException() {
        String username = "username";
        when(repo.findByUsername(username)).thenReturn(Optional.ofNullable(null));
        exception.expect(UsernameNotFoundException.class);
        exception.expectMessage("No user with "
                + "the name " + username + "was found in the database");

        customUserDetailsService.loadUserByUsername(username);

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
