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
    public static Response toRegisterFaqResponse(Long id) {
        return Response.ok();
    }

    // faq 리스트 조회 Response
    public static Object toSearchFaqListResponse(List<Faq> faqs) {
        List<FaqDto.FaqListResponse> faqListResponses = new ArrayList<>();

        faqs.stream().forEach(faq -> faqListResponses.add(new FaqDto.FaqListResponse()
                .setFaqId(faq.getId())
                .setCategoryName(faq.getCategoryName())
                .setFaqTitle(faq.getFaqTitle())
                .setRegisterDate(faq.getRegisterDate())
                .setNoticeDate(faq.getNoticeDate())
                .setFaqType(faq.getFaqType())));

        return faqListResponses;
    }

    // faq 상세 조회 Response
    public static Object toSearchFaqDetailResponse(Faq faq) {
        return new FaqDto.FaqDetailResponse()
                .setCategoryName(faq.getCategoryName())
                .setFaqTitle(faq.getFaqTitle())
                .setRegisterUser(faq.getRegisterUser())
                .setRegisterDate(faq.getRegisterDate())
                .setUpdateUser(faq.getUpdateUser())
                .setUpdateDate(faq.getUpdateDate())
                .setNoticeDate(faq.getNoticeDate())
                .setFaqType(faq.getFaqType())
                .setUpdateReason(faq.getUpdateReason())
                .setContent(faq.getContent());
    }

    // faq 수정 Response
    public static FaqDto toUpdateFaqResponse(Faq faq) {
        return ObjectMapperUtils.map(faq, FaqDto.class);
    }

    // faq 삭제 Response
    public static FaqDto toDeleteFaqResponse(Faq faq) {return ObjectMapperUtils.map(faq, FaqDto.class);}
}
