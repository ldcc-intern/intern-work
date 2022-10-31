package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.NoticeDto;
import kr.ldcc.internwork.model.entity.Notice;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeMapper {
    public static NoticeDto.CreateNoticeResponse toCreateNoticeResponse(Notice notice) {
        return new NoticeDto.CreateNoticeResponse().setId(notice.getId());
    }

    public static Page<NoticeDto.GetNoticeListResponse> toGetNoticeListResponse(Page<Notice> notices) {
        return notices.map(
                notice -> NoticeDto.GetNoticeListResponse.builder()
                        .no(notices.getContent().indexOf(notice))
                        .title(notice.getTitle())
                        .registerUser(notice.getRegisterUser().getName())
                        .registerDate(notice.getRegisterDate())
                        .noticeDate(notice.getNoticeDate())
                        .state(notice.getState())
                        .view(notice.getView())
                        .build()
        );
    }

    public static NoticeDto.GetDetailNoticeResponse toGetDetailNoticeResponse(Notice notice) {
        return new NoticeDto.GetDetailNoticeResponse()
                .setId(notice.getId())
                .setRegisterDate(notice.getRegisterDate())
                .setUpdateDate(notice.getUpdateDate())
                .setContent(notice.getContent())
                .setReason(notice.getReason())
                .setNoticeDate(notice.getNoticeDate())
                .setState(notice.getState())
                .setTitle(notice.getTitle())
                .setView(notice.getView())
                .setRegisterUser(notice.getRegisterUser().getName())
                .setUpdateUser(notice.getUpdateUser() != null ? notice.getUpdateUser().getName() : null);
    }

    public static NoticeDto.UpdateNoticeResponse toUpdateNoticeResponse(Notice notice) {
        return new NoticeDto.UpdateNoticeResponse()
                .setId(notice.getId())
                .setRegisterDate(notice.getRegisterDate())
                .setUpdateDate(notice.getUpdateDate())
                .setContent(notice.getContent())
                .setReason(notice.getReason())
                .setNoticeDate(notice.getNoticeDate())
                .setState(notice.getState())
                .setTitle(notice.getTitle())
                .setView(notice.getView())
                .setRegisterUser(notice.getRegisterUser().getName())
                .setUpdateUser(notice.getUpdateUser().getName());
    }

    public static NoticeDto.DeleteNoticeResponse toDeleteNoticeResponse(Optional<Notice> notice) {
        return new NoticeDto.DeleteNoticeResponse().setId(notice.get().getId());
    }
}
