package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class NoticeDto {
    private Long id;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;
    private String content;
    private String reason;
    private LocalDateTime startDate;
    private NoticeType state;
    private String title;
    private Integer view;
    private User registerUser;
    private User updateUser;

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
    public static class GetDetailNoticeResponse {
        private Long id;
        private LocalDateTime registerDate;
        private LocalDateTime updateDate;
        private String content;
        private String reason;
        private LocalDateTime startDate;
        private NoticeType state;
        private String title;
        private Integer view;
        private User registerUser;
        private User updateUser;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class NoticeListResponse {
        private Long id;
        private String title;
        private User registerUser;
        private LocalDateTime registerDate;
        private LocalDateTime startDate;
        private NoticeType state;
        private Integer view;
    }
}
