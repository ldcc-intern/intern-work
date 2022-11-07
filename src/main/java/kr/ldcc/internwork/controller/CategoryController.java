package kr.ldcc.internwork.controller;


import kr.ldcc.internwork.model.dto.request.CategoryRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.mapper.CategoryMapper;
import kr.ldcc.internwork.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return Response.ok().setData(CategoryMapper.toGetCategoryDetailResponse(categoryService.getCategoryDetail(categoryId)));
    }


    /** * * * * * * * * *
     *                  *
     *   category 수정   *
     *                  *
     * * * * * * * * * **/
    @PutMapping("/category/{categoryId}")
    public Response updateCategory(@PathVariable("categoryId") Long categoryId,
                                   @RequestBody @Valid CategoryRequest.UpdateCategoryRequest updateCategoryRequest){
        return Response.ok().setData(CategoryMapper.toUpdateCategoryResponse(categoryService.updateCategory(categoryId, updateCategoryRequest)));
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
