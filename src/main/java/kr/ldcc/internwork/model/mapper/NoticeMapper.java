package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.NoticeDto;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Notice;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeMapper {
    public static Response toCreateNoticeResponse(Long noticeId) {
        return Response.ok().setData(new NoticeDto.CreateNoticeResponse().setId(noticeId));
    }

    public static Response toGetNoticeListResponse(List<Notice> notices) {
        List<NoticeDto.GetNoticeListResponse> noticeListResponses = notices.stream().map(notice -> NoticeDto.GetNoticeListResponse.builder()
                .no(notices.indexOf(notice))
                .title(notice.getTitle())
                .registerUser(notice.getRegisterUser().getName())
                .registerDate(notice.getRegisterDate())
                .noticeDate(notice.getNoticeDate())
                .state(notice.getState())
                .view(notice.getView())
                .build()).collect(Collectors.toList());
        return Response.ok().setData(noticeListResponses);
    }

    public static Response toGetDetailNoticeResponse(Notice notice) {
        return Response.ok();
    }

    public static Response toUpdateNoticeResponse(Notice notice) {
        return Response.ok().setData(ObjectMapperUtils.map(notice, NoticeDto.class));
    }

    public static Response toDeleteNoticeResponse() {
        return Response.ok();
    }
}
