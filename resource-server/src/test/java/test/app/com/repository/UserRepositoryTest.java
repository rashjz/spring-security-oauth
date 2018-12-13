package test.app.com.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import test.app.com.domain.Courses;
import test.app.com.domain.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * UserRepositoryTest test cases of {@link UserRepository}
 **/
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void testSaveUserEntity() {
        User expectedOrder = entityManager.persist(expectedUser());
        User actualOrder = userRepository.getOne(expectedOrder.getUserId());

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testGetAllUserEntity() {
        entityManager.persist(expectedUser());
        List<User> userList = userRepository.findAll();

        assertNotNull(userList);
    }

    @Test
    public void testDeleteUserEntity() {
        User user = entityManager.persist(expectedUser());
        userRepository.delete(user);
        Optional<User> actual = userRepository.findById(user.getUserId());
        assertFalse(actual.isPresent());
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
