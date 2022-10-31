package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.common.types.CategoryType;
import kr.ldcc.internwork.model.dto.CategoryDto;
import kr.ldcc.internwork.model.dto.request.CategoryRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Category;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.model.mapper.CategoryMapper;
import kr.ldcc.internwork.repository.CategoryRepository;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    /** * * * * * * *  *
     *                 *
     *  category 등록   *
     *                 *
     * * * * * * * * * */
    @Transactional
    public Response createCategory(CategoryRequest.CreateCategoryRequest createCategoryRequest) {

        User user = userRepository.findById(createCategoryRequest.getRegisterUserId()).orElseThrow(() -> {
            log.error("createCategory Exception : [존재하지 않는 User Id]");
            return new InternWorkException.dataNotFoundException();
        });
        Category category = Category.builder()
                .mainCategory(createCategoryRequest.getMainCategory())
                .categoryName(createCategoryRequest.getCategoryName())
                .categoryType(createCategoryRequest.getCategoryType())
                .registerUser(user)
                .authInfo(createCategoryRequest.getAuthInfo())
                .orderId(createCategoryRequest.getOrderId())
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

    /** * * * * * * *  * * * *
     *                       *
     *  category 리스트 조회   *
     *                       *
     * * * * * * * * * * * * */
    @Transactional
    public Object getCategoryList(){

        List<Category> categories = categoryRepository.findAll(Sort.by("orderId"));
        return CategoryMapper.toGetCategoryListResponse(categories);
    }

    /** * * * * * * *  * * *
     *                     *
     *  category 상세 조회   *
     *                     *
     * * * * * * * * * * * */
    @Transactional
    public Category getCategoryDetail(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->{
            log.error("getDetailCategory Exception : [존재하지 않는 Category ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataDuplicateException();});

        return category;
    }


    /** * * * * * * *  * * *
     *                     *
     *  category 중복 체크   *
     *                     *
     * * * * * * * * * * * */
    public Response getDuplicateCategory(String categoryName) {

        Optional<Category> byCategoryName = Optional.ofNullable(categoryRepository.findByCategoryName(categoryName));
        if(byCategoryName.isPresent()) {
            log.error("getDuplicateCctv Exception : {}", ExceptionCode.DATA_DUPLICATE_EXCEPTION);
            return Response.ok().setData(new CategoryDto.CategoryDuplicateResponse().setResult(true));
        }
        return Response.ok().setData(new CategoryDto.CategoryDuplicateResponse().setResult(false));
    }



    /** * * * * * * * * *
     *                  *
     *   category 수정   *
     *                  *
     * * * * * * * * * **/
    @Transactional
    public Category updateCategory(Long categoryId, CategoryRequest.UpdateCategoryRequest updateCategoryRequest){

        // Null 일 경우 , ERROR  발생
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.error("updateCategory Exception : [존재하지 않는 Category ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION) ;
            return new InternWorkException.dataUpdateException();
        });

        User user = userRepository.findById(updateCategoryRequest.getUpdateUserId()).orElseThrow(() -> {
            log.error("updateNotice Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });

        // Null 이 아니면
        category.updateCategoryName(updateCategoryRequest.getCategoryName() !=null ? updateCategoryRequest.getCategoryName():category.getCategoryName());
        category.updateCategoryType(updateCategoryRequest.getCategoryType() !=null? updateCategoryRequest.getCategoryType():category.getCategoryType());
        category.updateMainCategory(updateCategoryRequest.getMainCategory() !=null? updateCategoryRequest.getMainCategory():category.getMainCategory());
        category.updateUpdateUser(user);

        try{
            categoryRepository.save(category);
        } catch(Exception e) {
            log.error("updateCategory Exception : {}", e.getMessage());
            throw new InternWorkException.dataUpdateException();
        }

        return category;

    }

    /** * * * * * * *  *
     *                 *
     *  category 삭제   *
     *                 *
     * * * * * * * * * */
    @Transactional
    public Response deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if(category.isPresent()){
            try{
                categoryRepository.deleteById(categoryId);
            } catch (Exception e){
                log.error("deleteCategory Exception : {}", e.getMessage());
                throw new InternWorkException.dataDeleteException();
            }

            return Response.ok();
        }
        log.error("deleteCategory Exception : {}", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        throw new InternWorkException.dataNotFoundException();

    }

    public Object getCategoryTypes() {
        CategoryType[] categoryTypes = CategoryType.values();

        return categoryTypes;
    }
}
