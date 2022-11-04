package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);

    Category findByOrderId(Integer orderId);

}
