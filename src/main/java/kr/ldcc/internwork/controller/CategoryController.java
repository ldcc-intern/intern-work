package kr.ldcc.internwork.controller;


import kr.ldcc.internwork.model.dto.CategoryDto;
import kr.ldcc.internwork.model.dto.request.CategoryRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.CategoryService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController (CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /** * * * * * * *  *
     *                 *
     *  category 등록   *
     *                 *
     * * * * * * * * * */
    @PostMapping("/category")
    public Response createCategory(@RequestBody @Valid CategoryRequest.CreateCategoryRequest createCategoryRequest){
        return categoryService.createCategory(createCategoryRequest);
    }

    /** * * * * * * *  * * * *
     *                       *
     *  category 리스트 조회   *
     *                       *
     * * * * * * * * * * * * */
    @GetMapping("/category")
    public Object getCategoryList(){
        return categoryService.getCategoryList();
    }

    /** * * * * * * *  * * *
     *                     *
     *  category 상세 조회   *
     *                     *
     * * * * * * * * * * * */
    @GetMapping("/category/{categoryId}")
    public Response getDetailCategory(@PathVariable("categoryId") Long categoryId) {
        return Response.ok().setData(categoryService.getCategoryDetail(categoryId));
    }

    /** * * * * * * *  * * *
     *                     *
     *  category 중복 체크   *
     *                     *
     * * * * * * * * * * * */

    @GetMapping("/category/{categoryName}/duplicate")
    public Response getDuplicateCategory(@PathVariable("categoryName") String categoryName) {
        return categoryService.getDuplicateCategory(categoryName);
    }

    /** * * * * * * * * *
     *                  *
     *   category 수정   *
     *                  *
     * * * * * * * * * **/
    @PutMapping("/category/{categoryId}")
    public Response updateCategory(@PathVariable("categoryId") Long categoryId,
                                   @RequestBody @Valid CategoryRequest.UpdateCategoryRequest updateCategoryRequest) {
        Optional<CategoryDto> CategoryDto = Optional.ofNullable(categoryService.updateCategory(categoryId, updateCategoryRequest));

        if(CategoryDto.isPresent()) {
            //Response 설정
            return Response.ok();
        }
        return Response.dataNotFoundException();
    }

    /** * * * * * * *  *
     *                 *
     *  category 삭제   *
     *                 *
     * * * * * * * * * */
    @DeleteMapping("/category/{categoryId}")
    public Response deleteCategory(@PathVariable("categoryId") Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
