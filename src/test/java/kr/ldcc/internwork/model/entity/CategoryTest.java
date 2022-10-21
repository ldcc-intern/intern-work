package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.common.types.CategoryType;
import kr.ldcc.internwork.controller.CategoryController;
import kr.ldcc.internwork.model.dto.request.CategoryRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.repository.CategoryRepository;
import kr.ldcc.internwork.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@Transactional
class CategoryTest {

    @Autowired CategoryRepository categoryRepository;

    @Autowired CategoryController categoryController;

    @Autowired CategoryService categoryService;

    @BeforeEach
    public void before(){
        Category category = Category.builder()
                .categoryName("Test 카테고리명")
                .mainCategory("Faq 카테고리")
                .useState(CategoryType.ON)
                .authInfo("테스트인증")
                .build();

        categoryRepository.save(category);
    }

    @Test
    public void 카테고리등록() throws Exception {
        CategoryRequest.CreateCategoryRequest createCategoryRequest = new CategoryRequest.CreateCategoryRequest();

        createCategoryRequest.setCategoryName("2233");
        createCategoryRequest.setMainCategory("메인");
        createCategoryRequest.setUseState(CategoryType.ON);
        createCategoryRequest.setAuthInfo("인증2");

        Response category = categoryController.createCategory(createCategoryRequest);

        assertEquals(categoryRepository.findByCategoryName("2233").getAuthInfo(), "인증2");

    }


}