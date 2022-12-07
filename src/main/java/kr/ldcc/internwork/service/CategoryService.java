package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException.*;
import kr.ldcc.internwork.model.dto.CategoryDto;
import kr.ldcc.internwork.model.dto.CategoryDto.CategoryDetail;
import kr.ldcc.internwork.model.dto.CategoryDto.CategoryList;
import kr.ldcc.internwork.model.dto.request.CategoryRequest;
import kr.ldcc.internwork.model.entity.Category;
import kr.ldcc.internwork.model.entity.Faq;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.repository.CategoryRepository;
import kr.ldcc.internwork.repository.FaqRepository;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final FaqRepository faqRepository;
    private final UserRepository userRepository;

    /**
     * * * * * * *  *
     * *
     * category 등록   *
     * *
     * * * * * * * * *
     */
    public void createCategory(CategoryRequest.CreateCategoryRequest createCategoryRequest) {
        User user = userRepository.findById(createCategoryRequest.getRegisterUserId()).orElseThrow(() -> {
            log.error("createCategory Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        // 카테고리 이름 중복 체크
        Optional<Category> categoryCheck = Optional.ofNullable(categoryRepository.findByCategoryName(createCategoryRequest.getCategoryName()));
        if (categoryCheck.isPresent()) {
            log.error("createCategory Exception : [카테고리 이름 중복]", ExceptionCode.DATA_DUPLICATE_EXCEPTION);
            throw new DataDuplicateException(ExceptionCode.DATA_DUPLICATE_EXCEPTION);
        }
        Category category = Category.builder()
                .mainCategory("FAQ 카테고리")
                .categoryName(createCategoryRequest.getCategoryName())
                .categoryType(createCategoryRequest.getCategoryType())
                .registerUser(user)
                .orderId(categoryRepository.findAll().size())
                .build();
        try {
            // category 정보 저장
            categoryRepository.save(category);
        } catch (Exception e) { // 카테고리 정보 저장 실패
            log.error("createCategory Exception : {}", e.getMessage());
            throw new DataSaveException(ExceptionCode.DATA_SAVE_EXCEPTION);
        }
    }

    /**
     * * * * * * *  * * * *
     * *
     * category 리스트 조회   *
     * *
     * * * * * * * * * * * *
     */
    public List<CategoryList> getCategoryList() {
        return CategoryDto.buildCategoryList(categoryRepository.findAll(Sort.by("orderId")));
    }

    /**
     * * * * * * *  * * *
     * *
     * category 상세 조회   *
     * *
     * * * * * * * * * * *
     */
    public CategoryDetail getCategoryDetail(Long categoryId) {
        return CategoryDto.buildCategoryDetail(categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.error("getDetailCategory Exception : [존재하지 않는 Category ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        }));
    }

    /**
     * * * * * * * * *
     * *
     * category 수정   *
     * *
     * * * * * * * * *
     **/
    public void updateCategory(Long categoryId, CategoryRequest.UpdateCategoryRequest updateCategoryRequest) {
        // Null 일 경우 , ERROR  발생
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.error("updateCategory Exception : [존재하지 않는 Category ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        User user = userRepository.findById(updateCategoryRequest.getUpdateUserId()).orElseThrow(() -> {
            log.error("updateNotice Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        // 카테고리 이름 중복 체크
        Optional<Category> categoryCheck = Optional.ofNullable(categoryRepository.findByCategoryName(updateCategoryRequest.getCategoryName()));
        if (categoryCheck.isPresent()) {
            log.error("createCategory Exception : [카테고리 이름 중복]", ExceptionCode.DATA_DUPLICATE_EXCEPTION);
            throw new DataDuplicateException(ExceptionCode.DATA_DUPLICATE_EXCEPTION);
        }
        if (updateCategoryRequest.getUpDown() != null) {
            // orderId 순서 이동
            if (updateCategoryRequest.getUpDown()) { // up 일 경우 [1]
                if (category.getOrderId() == 0) {
                    log.error("updateCategory Exception : [카테고리 위로 이동 불가]");
                    throw new CanNotMoveException(ExceptionCode.CAN_NOT_MOVE_EXCEPTION);
                }
                Category upCategory = categoryRepository.findByOrderId(category.getOrderId() - 1);
                upCategory.updateUpdateUser(user);
                upCategory.updateOrderId(category.getOrderId());
                category.updateOrderId(category.getOrderId() - 1);
                categoryRepository.save(upCategory);
            }
            if (!updateCategoryRequest.getUpDown()) { // down 일 경우 [0]
                if (category.getOrderId() == categoryRepository.findAll().size() - 1) {
                    log.error("updateCategory Exception : [카테고리 아래로 이동 불가]");
                    throw new CanNotMoveException(ExceptionCode.CAN_NOT_MOVE_EXCEPTION);
                }
                Category downCategory = categoryRepository.findByOrderId(category.getOrderId() + 1);
                downCategory.updateUpdateUser(user);
                downCategory.updateOrderId(category.getOrderId());
                category.updateOrderId(category.getOrderId() + 1);
                categoryRepository.save(downCategory);
            }
        }
        // Null 이 아니면
        category.updateCategoryName(updateCategoryRequest.getCategoryName() != null ? updateCategoryRequest.getCategoryName() : category.getCategoryName());
        category.updateCategoryType(updateCategoryRequest.getCategoryType() != null ? updateCategoryRequest.getCategoryType() : category.getCategoryType());
        category.updateUpdateUser(user);
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            log.error("updateCategory Exception : {}", e.getMessage());
            throw new DataUpdateException(ExceptionCode.DATA_UPDATE_EXCEPTION);
        }
    }

    /**
     * * * * * * *  *
     * *
     * category 삭제   *
     * *
     * * * * * * * * *
     */
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.error("updateCategory Exception : [존재하지 않는 Category ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        // 해당 카테고리에 faq 존재시 삭제 불가
        List<Faq> faqs = faqRepository.findByCategoryId(categoryId);
        if (faqs != null && !faqs.isEmpty()) {
            System.out.println(faqs);
            log.error("Delete Category Exception : [해당 카테고리에 faq 존재 -> 삭제 불가]");
            throw new ReferentialException(ExceptionCode.REFERENTIAL_EXCEPTION);
        }
        try {  // orderId 수정
            if (categoryRepository.findAll().size() - 1 != category.getOrderId()) { // 맨 끝 OrderId 가 아닐경우
                List<Category> categories = categoryRepository.findAll();
                for (Category changeOrderId : categories) {
                    if (changeOrderId.getOrderId() > category.getOrderId()) {
                        changeOrderId.updateOrderId(changeOrderId.getOrderId() - 1);
                    }
                }
            }
            categoryRepository.deleteById(categoryId);
        } catch (Exception e) {
            log.error("deleteCategory Exception : {}", e.getMessage());
            throw new DataDeleteException(ExceptionCode.DATA_DELETE_EXCEPTION);
        }
    }
}
