package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeMapper {
    public static Response toCreateNoticeResponse() {
        return Response.ok();
    }

    public static Response toGetNoticeListResponse() {
        return Response.ok();
    }

    public static Response toGetDetailNoticeResponse() {
        return Response.ok();
    }

    public static Response toUpdateNoticeResponse() {
        return Response.ok();
    }

    public static Response toDeleteNoticeResponse() {
        return Response.ok();
    }
}
