package kr.ldcc.internwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.ldcc.internwork.common.types.CategoryType;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

public class CategoryRequest {

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CreateCategoryRequest {

        private String mainCategory; // 대분류

        private String categoryName; // 카테고리명

        private CategoryType useState; // 사용 여부

        private Integer orderId; // 카테고리 순서

        private User registerUser; // 등록자

        private LocalDateTime registerDate; // 등록일

    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetCategoryRequest {

    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UpdateCategoryRequest {

        private Long categoryId; // 카테고리 ID

        private String mainCategory; // 대분류

        private String categoryName; // 카테고리명

        private CategoryType useState; // 사용 여부

        private Integer orderId; // 카테고리 순서

        private User registerUser; // 등록자

        private LocalDateTime registerDate; // 등록일

        private User updateUser; // 수정자

        private LocalDateTime updateDate; // 수정일

    }
}
