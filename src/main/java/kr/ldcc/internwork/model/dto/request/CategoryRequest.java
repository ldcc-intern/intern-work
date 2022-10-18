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
    public static class RegisterCategoryRequest {

        private String categoryName;

        private CategoryType categoryType;

        private Integer orderId; // 카테고리 순서

        private User userId;

    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchCategoryRequest {

    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UpdateCategoryRequest {

        private Long categoryId;

        private String categoryName;

        private CategoryType categoryType;

        private Integer orderId;

        private User userId;

    }
}
