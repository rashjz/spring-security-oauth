package test.app.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import test.app.com.domain.User;
import test.app.com.exception.UserNotFoundException;
import test.app.com.repository.UserRepository;

import java.util.Optional;

@Slf4j
@RestController
public class HomeController {

    private final UserRepository userRepository;

    @Autowired
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping(value = "/private")
    public String privateArea( ) {
        log.info("privateArea invoked {}",1);
        return "private";
    }


    @GetMapping(value = "/user/{userId}")
    public User getUserCourses(@PathVariable(name = "userId") Long userId) throws UserNotFoundException{
        log.debug("getUserCourses invoked with userId : {}", userId);
        return Optional.ofNullable(userRepository.getOne(userId))
                .orElseThrow(() -> new UserNotFoundException("user not found with given user Id",404));

    }

}
