package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.NoticeDto;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Notice;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeMapper {
    public static Response toCreateNoticeResponse(NoticeDto.CreateNoticeResponse createNoticeResponse) {
        return Response.ok().setData(createNoticeResponse);
    }

    public static Response toGetNoticeListResponse(Page<NoticeDto.GetNoticeListResponse> getNoticeListResponses) {
        return Response.ok().setData(getNoticeListResponses);
    }

    public static Response toGetDetailNoticeResponse(NoticeDto.GetDetailNoticeResponse getDetailNoticeResponse) {
        return Response.ok().setData(getDetailNoticeResponse);
    }

    public static Response toUpdateNoticeResponse(NoticeDto.UpdateNoticeResponse updateNoticeResponse) {
        return Response.ok().setData(updateNoticeResponse);
    }

    public static Response toDeleteNoticeResponse(NoticeDto.DeleteNoticeResponse deleteNoticeResponse) {
        return Response.ok().setData(deleteNoticeResponse);
    }
}
