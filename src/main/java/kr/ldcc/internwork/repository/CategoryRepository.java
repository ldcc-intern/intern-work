package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.common.types.CategoryType;
import kr.ldcc.internwork.model.entity.Category;
import kr.ldcc.internwork.model.entity.Faq;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllById(Long categoryId);

    Category findByCategoryName(String categoryName);



}
