package kr.ldcc.internwork.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.entity.Category;
import kr.ldcc.internwork.model.entity.User;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FaqDto {

    private String Content;
    private String updateReason;
    private LocalDateTime noticeDate;
    private FaqType faqType;
    private String faqTitle;


    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class FaqListResponse {

        private Integer no;
        private String categoryName;
        private String faqTitle;
        private LocalDateTime registerDate;
        private String registerUserName;
        private FaqType faqType;
        private LocalDateTime noticeDate;

        @Builder
        public FaqListResponse(Integer no, String categoryName, String faqTitle, LocalDateTime registerDate, LocalDateTime noticeDate, String registerUserName, FaqType faqType) {
            this.no = no;
            this.categoryName = categoryName;
            this.faqTitle = faqTitle;
            this.registerDate = registerDate;
            this.noticeDate = noticeDate;
            this.registerUserName = registerUserName;
            this.faqType = faqType;
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class FaqDetailResponse { // faq 상세 조회

        private String categoryName;
        private String faqTitle;
        private String registerUserName;
        private String updateUserName;
        private LocalDateTime registerDate;
        private LocalDateTime updateDate;
        private LocalDateTime noticeDate;
        private FaqType faqType;
        private String updateReason;
        private String content;

    }


    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class UpdateFaqResponse {
        private Long id;
        private LocalDateTime registerDate;
        private LocalDateTime updateDate;
        private LocalDateTime noticeDate;
        private String content;
        private String updateReason;
        private FaqType faqType;
        private String faqTitle;
        private String registerUserName;
        private String updateUserName;
        private String categoryName;

    }



}
