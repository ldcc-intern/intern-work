package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByParent(Menu parent);

    List<Menu> findAllByParentOrderByOrderId(Menu parent);
}
