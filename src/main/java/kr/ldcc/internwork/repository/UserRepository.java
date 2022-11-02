package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
