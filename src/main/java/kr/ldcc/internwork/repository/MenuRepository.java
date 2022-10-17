package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
