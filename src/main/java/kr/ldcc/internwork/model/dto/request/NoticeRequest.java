package kr.ldcc.internwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.ldcc.internwork.common.types.NoticeType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

public class NoticeRequest {
    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
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
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
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
