package kr.ldcc.internwork.model.dto.request;

import kr.ldcc.internwork.common.types.CategoryType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class CategoryRequest {
    @Getter
    public static class CreateCategoryRequest {
        @NotNull
        private String categoryName; // 카테고리명
        @NotNull
        private CategoryType categoryType; // 사용 여부
        @NotNull
        private Long registerUserId; // 등록자
    }

    @Getter
    public static class UpdateCategoryRequest {
        private Long categoryId; // 카테고리 ID
        private String categoryName; // 카테고리명
        private CategoryType categoryType; // 사용 여부
        private Boolean upDown; // 순서이동
        @NotNull
        private Long updateUserId; // 수정자
    }
}
