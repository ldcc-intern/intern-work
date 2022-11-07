package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.CategoryType;
import kr.ldcc.internwork.model.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CategoryDto {

    private Category categoryName;
    private String mainCategory;
    private Long orderId;
    private CategoryType useState;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class CreateCategoryResponse {
        private Long id;
    }



    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class CategoryListResponse { // 카테고리 리스트 조회

        private Long categoryId;
        private String categoryName;
        private Integer orderId;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class CategoryDetailResponse { // 카테고리 상세 조회

        private String mainCategory;
        private String categoryName;
        private CategoryType categoryType;
        private String registerUserName;
        private String updateUserName;
        private String registerDate;
        private String updateDate;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class UpdateCategoryResponse { // 카테고리 업데이트

        private Long id;
        private String mainCategory;
        private String categoryName;
        private CategoryType categoryType;
        private String registerUserName;
        private String updateUserName;
        private String registerDate;
        private String updateDate;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class CategoryDuplicateResponse{
        private boolean result;
    }


}
