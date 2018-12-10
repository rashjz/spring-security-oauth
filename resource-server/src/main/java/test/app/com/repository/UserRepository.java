package test.app.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.app.com.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {

} 
