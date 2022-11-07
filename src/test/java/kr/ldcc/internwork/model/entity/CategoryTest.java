package kr.ldcc.internwork.model.entity;


import kr.ldcc.internwork.common.types.CategoryType;
import kr.ldcc.internwork.controller.CategoryController;
import kr.ldcc.internwork.controller.UserController;
import kr.ldcc.internwork.model.dto.request.CategoryRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.repository.CategoryRepository;
import kr.ldcc.internwork.repository.UserRepository;
import kr.ldcc.internwork.service.CategoryService;
import kr.ldcc.internwork.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@Transactional
class CategoryTest {

    // Category
    @Autowired CategoryRepository categoryRepository;

    @Autowired CategoryController categoryController;

    @Autowired CategoryService categoryService;


    // User
    @Autowired UserRepository userRepository;

    @Autowired UserController userController;

    @Autowired UserService userService;



    @BeforeEach
    public void before(){
        Category category = Category.builder()
                .categoryName("Test")
                .mainCategory("Test")
                .categoryType(CategoryType.ON)
                .registerUser(userRepository.findByName("사용자1"))
                .orderId(1)
                .build();

        categoryRepository.save(category);
    }

    @Test
    public void 카테고리등록() throws Exception {
        CategoryRequest.CreateCategoryRequest createCategoryRequest = new CategoryRequest.CreateCategoryRequest();

        createCategoryRequest.setCategoryName("카테고리명1");
        createCategoryRequest.setCategoryType(CategoryType.ON);


        Response category = categoryController.createCategory(createCategoryRequest);


    }

    @Test
    public void 카테고리리스트조회() throws Exception {
        List<Category> categoryList = categoryRepository.findAll();
        assertNotNull(categoryList.size());
    }

    @Test
    public void 카테고리상세조회() throws Exception {
        Category findCategory = categoryRepository.findByCategoryName("카테고리명1");

        Optional<Category> category = categoryRepository.findById(findCategory.getId());

        assertEquals(category.get().getCategoryName(), findCategory.getCategoryName());
    }

    @Test
    public void 카테고리중복체크_중복O() throws Exception {
        Category findCategory = categoryRepository.findByCategoryName("카테고리명1");

        Category duplicateCategory = categoryRepository.findByCategoryName(findCategory.getCategoryName());

        assertEquals(duplicateCategory.getCategoryName(), findCategory.getCategoryName());
    }



/**
    @Test
    public void 카테고리중복체크_중복X() throws Exception {
        Category findCategory = categoryRepository.findByCategoryName("Test");

        Response test = categoryController.getDuplicateCategory(findCategory.getCategoryName());

        assertEquals(test.getCode(), 1000);
    }
 */

    @Test
    public void 카테고리수정() throws Exception {
        Category findCategory = categoryRepository.findByCategoryName("카테고리명1");

        CategoryRequest.UpdateCategoryRequest updateCategoryRequest = new CategoryRequest.UpdateCategoryRequest();
        updateCategoryRequest.setCategoryName("카테고리수정확인");
        updateCategoryRequest.setCategoryType(CategoryType.OFF);

        categoryController.updateCategory(findCategory.getId(), updateCategoryRequest);

        Optional<Category> byId = categoryRepository.findById(findCategory.getId());

        assertEquals(byId.get().getCategoryName(), updateCategoryRequest.getCategoryName());
    }

    @Test
    public void 카테고리삭제() throws Exception {
        Category findCategory = categoryRepository.findByCategoryName("카테고리수정확인");

        categoryController.deleteCategory(findCategory.getId());

        Optional<Category> category = categoryRepository.findById(findCategory.getId());

        // false
        assertFalse(category.isPresent());
    }

}