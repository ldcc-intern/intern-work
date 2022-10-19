package kr.ldcc.internwork.model.mapper;


import kr.ldcc.internwork.model.dto.FaqDto;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Faq;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqMapper {

    // Faq 등록 Response
    public static Response toRegisterFaqResponse() {return Response.ok();}

    // faq 리스트 조회 Response
    public static Response toSearchFaqListResponse() {return Response.ok();}

    // faq 상세 조회 Response
    public static Response toSearchFaqDetailResponse() {return Response.ok();}

    // faq 수정 Response
    public static Response toUpdateFaqResponse() {return Response.ok();}

    // faq 삭제 Response
    public static Response toDeleteFaqResponse() {return Response.ok();}
}
