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
    public static class CreateNoticeResponse {
        private Long id;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class GetNoticeListResponse {
        private Integer no;
        private String title;
        private String registerUser;
        private LocalDateTime registerDate;
        private LocalDateTime NoticeDate;
        private NoticeType state;
        private Integer view;

        @Builder
        public GetNoticeListResponse(Integer no, String title, String registerUser, LocalDateTime registerDate, LocalDateTime noticeDate, NoticeType state, Integer view) {
            this.no = no;
            this.title = title;
            this.registerUser = registerUser;
            this.registerDate = registerDate;
            NoticeDate = noticeDate;
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
        private LocalDateTime registerDate;
        private LocalDateTime updateDate;
        private String content;
        private String reason;
        private LocalDateTime noticeDate;
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
        private LocalDateTime registerDate;
        private LocalDateTime updateDate;
        private String content;
        private String reason;
        private LocalDateTime noticeDate;
        private NoticeType state;
        private String title;
        private Integer view;
        private String registerUser;
        private String updateUser;
    }
}
