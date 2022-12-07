package kr.ldcc.internwork.model.dto.request;

import kr.ldcc.internwork.common.types.NoticeType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class NoticeRequest {
    @Getter
    public static class CreateNoticeRequest {
        @NotNull
        private String title;
        @NotNull
        private String date;
        @NotNull
        private String time;
        @NotNull
        private String content;
        private Long userId;
    }

    @Getter
    public static class UpdateNoticeRequest {
        @NotNull
        private String title;
        @NotNull
        private NoticeType state;
        @NotNull
        private String date;
        @NotNull
        private String time;
        @NotNull
        private String reason;
        @NotNull
        private String content;
        private Long userId;
    }
}
