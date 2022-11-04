package kr.ldcc.internwork.model.mapper;


import kr.ldcc.internwork.model.dto.FaqDto;
import kr.ldcc.internwork.model.entity.Faq;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqMapper {

    // Faq 등록 Response
    public static FaqDto.RegisterFaqResponse toRegisterFaqResponse(Faq faq) {
        return new FaqDto.RegisterFaqResponse().setId(faq.getId());
    }

    // faq 리스트 조회 Response
    public static Page<FaqDto.FaqListResponse> toSearchFaqListResponse(Page<Faq> faqs) {
        return faqs.map(
                faq -> FaqDto.FaqListResponse.builder()
                        .no(faq.getId())
                        .categoryName(faq.getCategory().getCategoryName())
                        .registerUserName(faq.getRegisterUser().getName())
                        .faqType(faq.getFaqType())
                        .faqTitle(faq.getFaqTitle())
                        .registerDate(faq.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                        .noticeDate(faq.getNoticeDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                        .build()

        );

    }

    // faq 상세 조회 Response
    public static FaqDto.FaqDetailResponse toSearchFaqDetailResponse(Faq faq) {
        return new FaqDto.FaqDetailResponse()
                .setCategoryName(faq.getCategory().getCategoryName())
                .setFaqTitle(faq.getFaqTitle())
                .setRegisterUserName(faq.getRegisterUser().getName())
                .setRegisterDate(faq.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")))
                .setUpdateUserName(faq.getUpdateUser() != null ? faq.getUpdateUser().getName() : null)
                .setUpdateDate(faq.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")))
                .setNoticeDate(faq.getNoticeDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")))
                .setFaqType(faq.getFaqType())
                .setUpdateReason(faq.getUpdateReason()!= null ? faq.getUpdateReason() : null)
                .setContent(faq.getContent());
    }

    // faq 수정 Response
    public static FaqDto.UpdateFaqResponse toUpdateFaqResponse(Faq faq) {
        return new FaqDto.UpdateFaqResponse()
                .setId(faq.getId())
                .setCategoryName(faq.getCategory().getCategoryName())
                .setFaqTitle(faq.getFaqTitle())
                .setRegisterUserName(faq.getRegisterUser().getName())
                .setRegisterDate(faq.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")))
                .setUpdateUserName(faq.getUpdateUser().getName())
                .setUpdateDate(faq.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")))
                .setNoticeDate(faq.getNoticeDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")))
                .setFaqType(faq.getFaqType())
                .setUpdateReason(faq.getUpdateReason())
                .setContent(faq.getContent());
    }

    // faq 삭제 Response
    public static FaqDto toDeleteFaqResponse(Faq faq) {return ObjectMapperUtils.map(faq, FaqDto.class);}
}
