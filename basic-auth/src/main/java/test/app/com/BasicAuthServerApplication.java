package test.app.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableConfigurationProperties
@SpringBootApplication
public class BasicAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicAuthServerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
//    @Bean
//    public CommandLineRunner setupDefaultUser(UserRepository repository) {
//        return args -> {
//            repository.save(User.builder()
//                    .total("total")
//                    .courses(Collections.singletonList(Courses.builder()
//                            .grade("test")
//                            .name("courseNmae")
//                            .build()))
//                    .userName("testUser")
//                    .build());
//
//        };
//
//
//    }
} 
