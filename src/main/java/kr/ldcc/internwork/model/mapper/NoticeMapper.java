package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.NoticeDto;
import kr.ldcc.internwork.model.entity.Notice;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeMapper {
    public static Page<NoticeDto.GetNoticeListResponse> toGetNoticeListResponse(Page<Notice> notices) {
        if (notices.hasContent()) {
            return notices.map(
                    notice -> NoticeDto.GetNoticeListResponse.builder()
                            .id(notice.getId())
                            .title(notice.getTitle())
                            .registerUser(notice.getRegisterUser().getName())
                            .registerDate(notice.getRegisterDate())
                            .noticeDate(notice.getNoticeDate())
                            .state(notice.getState())
                            .view(notice.getView())
                            .build());
        }
        return null;
    }

    public static NoticeDto.GetDetailNoticeResponse toGetDetailNoticeResponse(Notice notice) {
        return new NoticeDto.GetDetailNoticeResponse()
                .setId(notice.getId())
                .setRegisterDate(notice.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")))
                .setUpdateDate(notice.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")))
                .setContent(notice.getContent())
                .setReason(notice.getReason())
                .setNoticeDate(notice.getNoticeDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")))
                .setState(notice.getState())
                .setTitle(notice.getTitle())
                .setView(notice.getView())
                .setRegisterUser(notice.getRegisterUser().getName())
                .setUpdateUser(notice.getUpdateUser() != null ? notice.getUpdateUser().getName() : null);
    }

    public static NoticeDto.UpdateNoticeResponse toUpdateNoticeResponse(Notice notice) {
        return new NoticeDto.UpdateNoticeResponse()
                .setId(notice.getId())
                .setRegisterDate(notice.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")))
                .setUpdateDate(notice.getUpdateDate() != notice.getRegisterDate() ? notice.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")) : null)
                .setContent(notice.getContent())
                .setReason(notice.getReason())
                .setNoticeDate(notice.getNoticeDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")))
                .setState(notice.getState())
                .setTitle(notice.getTitle())
                .setView(notice.getView())
                .setRegisterUser(notice.getRegisterUser().getName())
                .setUpdateUser(notice.getUpdateUser().getName());
    }
}
