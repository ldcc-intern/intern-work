package kr.ldcc.internwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.common.types.validation.Enum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

public class NoticeRequest {
    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CreateNoticeRequest {
        private String title;
        private LocalDateTime notice_start;
        private String content;
        private Long user;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class UpdateNoticeRequest {
        private String title;
        @Enum(enumClass = NoticeType.class)
        private NoticeType state;
        private LocalDateTime notice_start;
        private String update_reason;
        private String content;
        private String user;
    }
}
