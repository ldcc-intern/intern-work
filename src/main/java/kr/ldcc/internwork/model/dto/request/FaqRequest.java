package kr.ldcc.internwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


public class FaqRequest {

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchFaqRequest { // 사용 안됨

        private Long faqId;

        private String categoryName;

        private String faqTitle;

        private User registerUser;

        private String noticeStart;

        private String noticeEnd;

        private FaqType faqType;

        private Integer faqPage;

        private Integer faqSort;

        private Integer faqSize;


    }


    /** * * * * * *
     *            *
     *  faq 등록   *
     *            *
     * * * * * * **/
    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RegisterFaqRequest {

        @NotNull
        private Long categoryId;

        @NotNull
        private String faqTitle;

        @NotNull
        private String noticeDate;

        @NotNull
        private String noticeTime;

        private FaqType faqType;

        @NotNull
        private String content;

        @NotNull
        private Long registerUserId;

    }


    /** * * * * * *
     *            *
     *  faq 수정   *
     *            *
     * * * * * * **/
    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UpdateFaqRequest {

        private Long categoryId;

        private String faqTitle;

        private String noticeDate;

        private String noticeTime;

        private FaqType faqType;

        private String content;

        @NotNull
        private String updateReason;

        @NotNull
        private Long updateUserId;
    }
}
