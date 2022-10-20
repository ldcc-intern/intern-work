package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.NoticeDto;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Notice;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeMapper {
    public static Response toCreateNoticeResponse(Long noticeId) {
        return Response.ok().setData(new NoticeDto.CreateNoticeResponse().setId(noticeId));
    }

    public static Response toGetNoticeListResponse(List<Notice> notices) {
        List<NoticeDto.NoticeListResponse> noticeListResponses = new ArrayList<>();
        notices.forEach(notice -> noticeListResponses.add(new NoticeDto.NoticeListResponse()
                .setId(notice.getId())
                .setTitle(notice.getTitle())
                .setRegisterUser(notice.getRegisterUser())
                .setRegisterDate(notice.getRegisterDate())
                .setStartDate(notice.getStartDateTime())
                .setState(notice.getState())
                .setView(notice.getView())
        ));
        return Response.ok().setData(noticeListResponses);
    }

    public static Response toGetDetailNoticeResponse(Notice notice) {
        return Response.ok().setData(new NoticeDto.GetDetailNoticeResponse()
                .setId(notice.getId())
                .setUpdateDate(notice.getUpdateDate())
                .setRegisterDate(notice.getRegisterDate())
                .setContent(notice.getContent())
                .setReason(notice.getReason())
                .setStartDate(notice.getStartDateTime())
                .setState(notice.getState())
                .setTitle(notice.getTitle())
                .setView(notice.getView())
                .setRegisterUser(notice.getRegisterUser())
                .setUpdateUser(notice.getUpdateUser())
        );
    }

    public static Response toUpdateNoticeResponse(Notice notice) {
        return Response.ok().setData(ObjectMapperUtils.map(notice, NoticeDto.class));
    }

    public static Response toDeleteNoticeResponse() {
        return Response.ok();
    }
}
