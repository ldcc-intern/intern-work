package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.common.types.CategoryType;
import kr.ldcc.internwork.model.dto.request.CategoryRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Category;
import kr.ldcc.internwork.model.mapper.CategoryMapper;
import kr.ldcc.internwork.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    // category 등록
    @Transactional
    public Response createCategory(CategoryRequest.CreateCategoryRequest createCategoryRequest) {
        Category category = Category.builder()
                .mainCategory(createCategoryRequest.getMainCategory())
                .categoryName(createCategoryRequest.getCategoryName())
                .useState(createCategoryRequest.getUseState())
                .registerUser(createCategoryRequest.getRegisterUser())
                .registerDate(createCategoryRequest.getRegisterDate())
                .build();

        try{
            // category 정보 저장
            categoryRepository.save(category);
        }
        // categoryName 중복 체크
        catch(Exception e){
            log.error("createCategory Exception : {}", e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }

        return Response.ok().setData(category.getId());
    }

    // category 리스트 조회
    @Transactional
    public Object getCategoryList(){

        List<Category> categories = categoryRepository.findAll(Sort.by("orderId"));
        return CategoryMapper.toGetCategoryListResponse(categories);
    }

    // category 상세 조회
    @Transactional
    public Object getCategoryDetail(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->{
            log.error("getDetailCategory Exception : [존재하지 않는 Category ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataDuplicateException();});

        return CategoryMapper.toGetCategoryDetailResponse(category);
    }

    // category 중복 체크


    // category 삭제
    @Transactional
    public Response deleteCategory() {
        return CategoryMapper.toDeleteCategoryResponse();
    }
}
