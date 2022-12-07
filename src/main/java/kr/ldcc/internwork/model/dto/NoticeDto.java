package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Notice;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class NoticeDto {
    private final Long id;
    private final String content;
    private final String noticeDate;
    private final String reason;
    private final NoticeType state;
    private final String title;
    private final Integer view;
    private final String registerUser;
    private final String updateUser;
    private final String registerDate;
    private final String updateDate;

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
    public static class NoticeList {
        private final Long id;
        private final String title;
        private final String registerUser;
        private final LocalDateTime registerDate;
        private final LocalDateTime NoticeDate;
        private final NoticeType state;
        private final Integer view;

        public NoticeList(Notice notice) {
            this.id = notice.getId();
            this.title = notice.getTitle();
            this.registerUser = notice.getRegisterUser().getName();
            this.registerDate = notice.getRegisterDate();
            this.NoticeDate = notice.getNoticeDate();
            this.state = notice.getState();
            this.view = notice.getView();
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NoticePage {
        private final Response.Pagination pagination;
        private final List<NoticeList> noticeList;

        public NoticePage(Page<Notice> page) {
            this.pagination = new Response.Pagination(page);
            this.noticeList = page.getContent().stream().map(NoticeList::new).collect(Collectors.toList());
        }
    }

    public static NoticePage buildNoticePage(Page<Notice> noticePage) {
        if (noticePage.hasContent()) {
            return new NoticePage(noticePage);
        }
        return null;
    }

    public static NoticeDto buildDetailNotice(Notice notice) {
        return new NoticeDto(notice);
    }
}
