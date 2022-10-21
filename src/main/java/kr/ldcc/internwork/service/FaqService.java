package kr.ldcc.internwork.service;


import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.dto.request.FaqRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Category;
import kr.ldcc.internwork.model.entity.Faq;
import kr.ldcc.internwork.model.mapper.FaqMapper;
import kr.ldcc.internwork.repository.FaqRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class FaqService {

    private final FaqRepository faqRepository;

    @Autowired
    public FaqService(FaqRepository faqRepository){
        this.faqRepository = faqRepository;
    }


    /** * * * * * *
     *            *
     *  faq 등록   *
     *            *
     * * * * * * **/
    @Transactional
    public Response registerFaq(FaqRequest.RegisterFaqRequest registerFaqRequest) {
        Faq faq = Faq.builder()
                .categoryName(registerFaqRequest.getCategoryName())
                .registerUser(registerFaqRequest.getRegisterUser())
                .content(registerFaqRequest.getContent())
                .build();

        try{
            // faq 정보 저장
            faqRepository.save(faq);
        }
        // faq 중복 체크
        catch (Exception e){
            log.error("registerFaq Exception : {}", e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }

        return Response.ok().setData(faq.getId());
    }

    /** * * * * * * * * *
     *                  *
     *  faq 리스트 조회   *
     *                  *
     * * * * * * * * * **/
    @Transactional
    public Object searchFaqList(String categoryName, LocalDateTime registerDateStart, LocalDateTime registerDateEnd, LocalDateTime noticeDateStart, LocalDateTime noticeDateEnd, FaqType state, String registerUser, String title) {
        return FaqMapper.toSearchFaqListResponse();
    }

    /** * * * * * * * *
     *                *
     *  faq 상세 조회   *
     *                *
     * * * * * * * * **/
    @Transactional
    public Response searchFaqDetail(Long FaqId) {
        return FaqMapper.toSearchFaqDetailResponse();
    }

    /** * * * * * *
     *            *
     *  faq 수정   *
     *            *
     * * * * * * **/
    @Transactional
    public Response updateFaq(Long faqId, FaqRequest.UpdateFaqRequest updateFaqRequest){
        return FaqMapper.toUpdateFaqResponse();
    }

    /** * * * * * *
     *            *
     *  faq 삭제   *
     *            *
     * * * * * * **/
    @Transactional
    public Response deleteFaq(Long faqId) {
        return FaqMapper.toDeleteFaqResponse();
    }
}
