package kr.ldcc.internwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.common.types.validation.Enum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class NoticeRequest {
    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CreateNoticeRequest {
        private String title;
        private String startDate;
        private String startTime;
        private String content;
        private Long userId;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UpdateNoticeRequest {
        private String title;
        @Enum(enumClass = NoticeType.class)
        private NoticeType state;
        private String startDate;
        private String startTime;
        private String reason;
        private String content;
        private Long userId;
    }
}
