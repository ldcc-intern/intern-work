package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.SimpleTimeZone;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
