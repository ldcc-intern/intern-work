package kr.ldcc.internwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.entity.Category;
import kr.ldcc.internwork.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;


public class FaqRequest {

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchFaqRequest {

        private Long faqId;

        private String categoryName;

        private String title;

        private String registerUser;

        private LocalDateTime registerDate;

        private LocalDateTime noticeDate;

        private FaqType faqType;

        private Integer faqPage;

        private Integer faqSort;

        private Integer faqSize;


    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RegisterFaqRequest {

        private Category categoryName;

        private String title;

        private LocalDateTime noticeDate;

        private FaqType faqType;

        private String content;

        private User userId;


    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UpdateFaqRequest {

        private Long categoryId;

        private String categoryName;

        private String title;

        private LocalDateTime noticeDate;

        private FaqType faqType;

        private String content;

        private String reason;

        private User userId;
    }
}
