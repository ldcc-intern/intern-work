package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.model.dto.request.CategoryRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * * * * * * *  *
     * *
     * category 등록   *
     * *
     * * * * * * * * *
     */
    @PostMapping
    public Response createCategory(@RequestBody @Valid CategoryRequest.CreateCategoryRequest createCategoryRequest) {
        log.info("[createCategory]");
        categoryService.createCategory(createCategoryRequest);
        return Response.ok();
    }

    /**
     * * * * * * *  * * * *
     * *
     * category 리스트 조회   *
     * *
     * * * * * * * * * * * *
     */
    @GetMapping
    public Response getCategoryList() {
        log.info("[getCategoryList]");
        return Response.ok().setData(categoryService.getCategoryList());
    }

    /**
     * * * * * * *  * * *
     * *
     * category 상세 조회   *
     * *
     * * * * * * * * * * *
     */
    @GetMapping("/{categoryId}")
    public Response getDetailCategory(@PathVariable("categoryId") Long categoryId) {
        log.info("[getDetailCategory]");
        return Response.ok().setData(categoryService.getCategoryDetail(categoryId));
    }


    /**
     * * * * * * * * *
     * *
     * category 수정   *
     * *
     * * * * * * * * *
     **/
    @PutMapping("/{categoryId}")
    public Response updateCategory(@PathVariable("categoryId") Long categoryId,
                                   @RequestBody @Valid CategoryRequest.UpdateCategoryRequest updateCategoryRequest) {
        log.info("[updateCategory]");
        categoryService.updateCategory(categoryId, updateCategoryRequest);
        return Response.ok();
    }

    /**
     * * * * * * *  *
     * *
     * category 삭제   *
     * *
     * * * * * * * * *
     */
    @DeleteMapping("/{categoryId}")
    public Response deleteCategory(@PathVariable("categoryId") Long categoryId) {
        log.info("[deleteCategory]");
        categoryService.deleteCategory(categoryId);
        return Response.ok();
    }
}
