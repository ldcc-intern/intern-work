package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.model.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByParent(Menu parent);

    ArrayList<Menu> findAllByParent(Sort sort, Menu parent);
}
