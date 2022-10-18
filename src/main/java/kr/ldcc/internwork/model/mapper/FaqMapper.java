package kr.ldcc.internwork.model.mapper;


import kr.ldcc.internwork.model.dto.FaqDto;
import kr.ldcc.internwork.model.entity.Faq;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqMapper {

    public static FaqDto tofaqDto(Faq faq) {return ObjectMapperUtils.map(faq, FaqDto.class);}



    // faq 리스트 조회 Response


    public static Object toFaqListResponse(List<Faq> faqs){
        List<FaqDto.FaqListResponse> faqListResponse = new ArrayList<>();

        faqs.stream().forEach(faq -> faqListResponse.add(new FaqDto.FaqListResponse()
                                    .setFaqId(faq.getId())
                                    .setCategoryName(faq.getCategoryName())
                                    .setTitle(faq.getTitle())
                                    .setRegisterDate(faq.getRegisterDate())
                                    .setRegisterUserName(faq.getRegisterUser())
                                    .setState(faq.getState())));

        return faqListResponse;
    }


    // faq 상세 조회 Response

    public static Object toFaqDetailResponse(Faq faq) {
        return new FaqDto.FaqDetailResponse()
                .setCategoryName(faq.getCategoryName())
                .setTitle(faq.getTitle())
                .setRegisterUser(faq.getRegisterUser())
                .setUpdateUser(faq.getUpdateUser())
                .setRegisterDate(faq.getRegisterDate())
                .setUpdateDate(faq.getUpdateDate())
                .setNoticeDate(faq.getNoticeDate())
                .setState(faq.getState())
                .setUpdateReason(faq.getUpdateReason())
                .setContent(faq.getContent());
    }

    // faq 수정 Response
    public static FaqDto toFaqUpdateResponse(Faq faq) { return ObjectMapperUtils.map(faq, FaqDto.class);}

}
