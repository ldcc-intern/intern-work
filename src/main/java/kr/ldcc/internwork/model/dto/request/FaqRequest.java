package kr.ldcc.internwork.model.dto.request;

import kr.ldcc.internwork.common.types.FaqType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class FaqRequest {
    /**
     * * * * * *
     * *
     * faq 등록   *
     * *
     * * * * * *
     **/
    @Getter
    public static class RegisterFaqRequest {
        @NotNull
        private String categoryName;
        @NotNull
        private String faqTitle;
        @NotNull
        private String noticeDate;
        @NotNull
        private String noticeTime;
        @NotNull
        private FaqType faqType;
        @NotNull
        private String content;
        @NotNull
        private Long registerUserId;
    }

    /**
     * * * * * *
     * *
     * faq 수정   *
     * *
     * * * * * *
     **/
    @Getter
    public static class UpdateFaqRequest {
        private String categoryName;
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
