package test.app.com.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import test.app.com.entities.Role;
import test.app.com.entities.User;
import test.app.com.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * UserRepositoryTest test cases of {@link UserRepository}
 **/
@RunWith(SpringRunner.class)
@DataJpaTest(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CommandLineRunner.class))
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void testSaveUserEntity() {
        User expectedUser = entityManager.persist(expectedUser());
        User actualUser = userRepository.getOne(expectedUser.getId());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetAllUserEntity() {
        User expectedUser = entityManager.persist(expectedUser());
        List<User> userList = userRepository.findAll();

        assertEquals(1, userList.size());
        assertEquals(expectedUser, userList.get(0));
    }

    @Test
    public void testDeleteUserEntity() {
        User user = entityManager.persist(expectedUser());
        userRepository.delete(user);
        Optional<User> actual = userRepository.findById(user.getId());
        assertFalse(actual.isPresent());
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
