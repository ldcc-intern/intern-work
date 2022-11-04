package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.CategoryDto;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        return ObjectMapperUtils.map(category, CategoryDto.class);
    }

    // category 생성 Response
    public static Response toCreateCategoryResponse(Long id) {

        return Response.ok().setData(new CategoryDto.CreateCategoryResponse().setId(id));
    }

    // category 리스트 조회 Response
    public static Object toGetCategoryListResponse(List<Category> categories) {

        List<CategoryDto.CategoryListResponse> categoryListResponses = new ArrayList<>();

        categories.stream().forEach(category -> categoryListResponses.add(new CategoryDto.CategoryListResponse()
                                                                            .setCategoryId(category.getId())
                                                                            .setCategoryName(category.getCategoryName())
                                                                            .setOrderId(category.getOrderId())));

        return categoryListResponses;
    }

    // category 상세 조회 Response
    public static CategoryDto.CategoryDetailResponse toGetCategoryDetailResponse(Category category) {
        return new CategoryDto.CategoryDetailResponse()
                .setMainCategory(category.getMainCategory())
                .setCategoryName(category.getCategoryName())
                .setCategoryType(category.getCategoryType())
                .setRegisterDate(category.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")))
                .setUpdateUserName(category.getUpdateUser() != null ? category.getUpdateUser().getName() : null)
                .setRegisterUserName(category.getResisterUser().getName())
                .setUpdateDate(category.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")));
    }

    // category 수정 Response
    public static CategoryDto.UpdateCategoryResponse toUpdateCategoryResponse(Category category) {
        return new CategoryDto.UpdateCategoryResponse()
                .setId(category.getId())
                .setMainCategory(category.getMainCategory())
                .setCategoryName(category.getCategoryName())
                .setCategoryType(category.getCategoryType())
                .setUpdateUserName(category.getUpdateUser().getName())
                .setRegisterUserName(category.getResisterUser().getName())
                .setRegisterDate(category.getRegisterDate())
                .setUpdateDate(category.getUpdateDate());
    }

    // category 삭제 Response
    public static Response toDeleteCategoryResponse() {
        return Response.ok();
    }
}
