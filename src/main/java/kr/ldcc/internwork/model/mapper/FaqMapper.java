package kr.ldcc.internwork.model.mapper;


import kr.ldcc.internwork.model.dto.FaqDto;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Faq;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqMapper {

    // Faq 등록 Response
    public static Response toRegisterFaqResponse(Long id) {
        return Response.ok();
    }

    // faq 리스트 조회 Response
    public static Page<FaqDto.FaqListResponse> toSearchFaqListResponse(Page<Faq> faqs) {
        return faqs.map(
                faq -> FaqDto.FaqListResponse.builder()
                        .no(faqs.getContent().indexOf(faq))
                        .categoryName(faq.getCategoryName())
                        .registerUserName(faq.getRegisterUser().getName())
                        .faqType(faq.getFaqType())
                        .faqTitle(faq.getFaqTitle())
                        .registerDate(faq.getRegisterDate())
                        .noticeDate(faq.getNoticeDate())
                        .build()

        );

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
