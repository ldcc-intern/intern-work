package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.NoticeDto;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Notice;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeMapper {
    public static Response toCreateNoticeResponse(Long noticeId) {
        return Response.ok().setData(new NoticeDto.CreateNoticeResponse().setId(noticeId));
    }

    public static Response toGetNoticeListResponse(Page<Notice> notices) {
        List<NoticeDto.GetNoticeListResponse> noticeListResponses = notices.hasContent() ? notices.getContent().stream().map(notice -> NoticeDto.GetNoticeListResponse.builder()
                .no(notices.getContent().indexOf(notice))
                .title(notice.getTitle())
                .registerUser(notice.getRegisterUser().getName())
                .registerDate(notice.getRegisterDate())
                .noticeDate(notice.getNoticeDate())
                .state(notice.getState())
                .view(notice.getView())
                .build()).collect(Collectors.toList()) : null;
        return Response.ok().setData(noticeListResponses);
    }

    public static Response toGetDetailNoticeResponse(Notice notice) {
        return Response.ok().setData(new NoticeDto.GetDetailNoticeResponse()
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
                .setUpdateUser(notice.getUpdateUser() != null ? notice.getUpdateUser().getName() : null)
        );
    }

    public static Response toUpdateNoticeResponse(Notice notice) {
        return Response.ok().setData(new NoticeDto.UpdateNoticeResponse()
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
                .setUpdateUser(notice.getUpdateUser().getName())
        );
    }

    public static Response toDeleteNoticeResponse() {
        return Response.ok();
    }
}
