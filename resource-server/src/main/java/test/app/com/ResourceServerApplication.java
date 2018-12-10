package test.app.com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import test.app.com.domain.Courses;
import test.app.com.domain.User;
import test.app.com.repository.UserRepository;

import java.util.Collections;

@SpringBootApplication
public class ResourceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }


    @Bean
    public CommandLineRunner setupDefaultUser(UserRepository repository) {
        return args -> {
            repository.save(User.builder()
                    .total("total")
                    .courses(Collections.singletonList(Courses.builder()
                            .grade("test")
                            .name("courseNmae")
                            .build()))
                    .userName("testUser")
                    .build());

        };


    }
} 
