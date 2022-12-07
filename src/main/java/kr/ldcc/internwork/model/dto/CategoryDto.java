package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.CategoryType;
import kr.ldcc.internwork.model.entity.Category;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDto {
    @Getter
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class CategoryList { // 카테고리 리스트 조회
        private final Long categoryId;
        private final String categoryName;
        private final Integer orderId;

        public CategoryList(Category category) {
            this.categoryId = category.getId();
            this.categoryName = category.getCategoryName();
            this.orderId = category.getOrderId();
        }
    }

    @Getter
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class CategoryDetail { // 카테고리 상세 조회
        private final String mainCategory;
        private final String categoryName;
        private final CategoryType categoryType;
        private final String registerUserName;
        private final String updateUserName;
        private final String registerDate;
        private final String updateDate;

        public CategoryDetail(Category category) {
            this.mainCategory = category.getMainCategory();
            this.categoryName = category.getCategoryName();
            this.categoryType = category.getCategoryType();
            this.registerUserName = category.getResisterUser().getName();
            this.updateUserName = category.getUpdateUser() != null ? category.getUpdateUser().getName() : null;
            this.registerDate = category.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm"));
            this.updateDate = category.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm"));
        }
    }

    public static List<CategoryList> buildCategoryList(List<Category> categoryList) {
        return categoryList.stream().map(CategoryList::new).collect(Collectors.toList());
    }

    public static CategoryDetail buildCategoryDetail(Category category) {
        return new CategoryDetail(category);
    }
}
