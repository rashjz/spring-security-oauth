package test.app.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import test.app.com.entities.Role;
import test.app.com.entities.User;
import test.app.com.services.UserService;

import java.util.Arrays;

@Component
public class JobCommandLineRunner implements CommandLineRunner {
    @Autowired
    @Qualifier(value = "userService")
    private UserService userService;


    @Override
    public void run(String... args) throws Exception {
        userService.save(new User(
                "auth-admin", //username
                "admin1", //password
                Arrays.asList(new Role("USER"), new Role("ACTUATOR")),//roles
                true//Active
        ));
    }
}
