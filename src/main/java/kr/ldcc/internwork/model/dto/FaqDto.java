package kr.ldcc.internwork.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.FaqType;
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


    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class RegisterFaqResponse { // Faq 등록

        private Long id;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class FaqListResponse {

        private Long no;
        private String categoryName;
        private String faqTitle;
        private String registerDate;
        private String registerUserName;
        private FaqType faqType;
        private String noticeDate;

        @Builder
        public FaqListResponse(Long no, String categoryName, String faqTitle, String registerDate, String noticeDate, String registerUserName, FaqType faqType) {
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
        private String registerDate;
        private String updateDate;
        private String noticeDate;
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
        private String registerDate;
        private String updateDate;
        private String noticeDate;
        private String content;
        private String updateReason;
        private FaqType faqType;
        private String faqTitle;
        private String registerUserName;
        private String updateUserName;
        private String categoryName;

    }



}
