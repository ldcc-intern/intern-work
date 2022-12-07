package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.entity.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class NoticeDto {
    private Long id;
    private String content;
    private String noticeDate;
    private String reason;
    private NoticeType state;
    private String title;
    private Integer view;
    private String registerUser;
    private String updateUser;
    private String registerDate;
    private String updateDate;

    public NoticeDto(Notice notice) {
        this.id = notice.getId();
        this.content = notice.getContent();
        this.noticeDate = notice.getNoticeDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.reason = notice.getReason();
        this.state = notice.getState();
        this.title = notice.getTitle();
        this.view = notice.getView();
        this.registerUser = notice.getRegisterUser().getName();
        this.updateUser = notice.getUpdateUser() != null ? notice.getUpdateUser().getName() : null;
        this.registerDate = notice.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.updateDate = notice.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
    }

    @Getter
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class GetNoticeList {
        private final Long id;
        private final String title;
        private final String registerUser;
        private final LocalDateTime registerDate;
        private final LocalDateTime NoticeDate;
        private final NoticeType state;
        private final Integer view;

        public GetNoticeList(Notice notice) {
            this.id = notice.getId();
            this.title = notice.getTitle();
            this.registerUser = notice.getRegisterUser().getName();
            this.registerDate = notice.getRegisterDate();
            this.NoticeDate = notice.getNoticeDate();
            this.state = notice.getState();
            this.view = notice.getView();
        }
    }

    public static Page<GetNoticeList> buildNoticePage(Page<Notice> noticePage) {
        if (noticePage.hasContent()) {
            return noticePage.map(GetNoticeList::new);
        }
        return null;
    }

    public static NoticeDto buildDetailNotice(Notice notice) {
        return new NoticeDto(notice);
    }
}
