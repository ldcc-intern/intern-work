package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.NoticeType;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

public class NoticeDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class GetNoticeListResponse {
        private Long id;
        private String title;
        private String registerUser;
        private LocalDateTime registerDate;
        private LocalDateTime NoticeDate;
        private NoticeType state;
        private Integer view;

        @Builder
        public GetNoticeListResponse(Long id, String title, String registerUser, LocalDateTime registerDate, LocalDateTime noticeDate, NoticeType state, Integer view) {
            this.id = id;
            this.title = title;
            this.registerUser = registerUser;
            this.registerDate = registerDate;
            this.NoticeDate = noticeDate;
            this.state = state;
            this.view = view;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class GetDetailNoticeResponse {
        private Long id;
        private String registerDate;
        private String updateDate;
        private String content;
        private String reason;
        private String noticeDate;
        private NoticeType state;
        private String title;
        private Integer view;
        private String registerUser;
        private String updateUser;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class UpdateNoticeResponse {
        private Long id;
        private String registerDate;
        private String updateDate;
        private String content;
        private String reason;
        private String noticeDate;
        private NoticeType state;
        private String title;
        private Integer view;
        private String registerUser;
        private String updateUser;
    }
}
