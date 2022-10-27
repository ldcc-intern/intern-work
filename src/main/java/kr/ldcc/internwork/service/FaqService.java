package kr.ldcc.internwork.service;


import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.dto.FaqDto;
import kr.ldcc.internwork.model.dto.request.FaqRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Category;
import kr.ldcc.internwork.model.entity.Faq;
import kr.ldcc.internwork.model.mapper.FaqMapper;
import kr.ldcc.internwork.repository.FaqRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jca.support.LocalConnectionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class FaqService {

    private final FaqRepository faqRepository;

    @Autowired
    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    /** * * * * *
     *          *
     * faq 등록  *
     *          *
     * * * * * **/
    @Transactional
    public Response registerFaq(FaqRequest.RegisterFaqRequest registerFaqRequest) {
        Faq faq = Faq.builder()
                .categoryName(registerFaqRequest.getCategoryName())
                .faqType(registerFaqRequest.getFaqType())
                .registerDate(registerFaqRequest.getRegisterDate())
                .noticeDate(registerFaqRequest.getNoticeDate())
                .faqTitle(registerFaqRequest.getFaqTitle())
                .registerUser(registerFaqRequest.getRegisterUser())
                .content(registerFaqRequest.getContent())
                .authInfo(registerFaqRequest.getAuthInfo())
                .build();

        // faq 정보 저장
        faqRepository.save(faq);


        return Response.ok().setData(faq.getId());
    }


    /** * * * * * * * * * **
     *                     *
     *  faq 리스트 전체 조회  *
     *                     *
     * * * * * * * * * * * */
/**
 *
    @Transactional
    public Object searchFaqAllList() {

        List<Faq> faqs = null;

        // 전체리스트 조회
        faqs = faqRepository.findAll();

        return FaqMapper.toSearchFaqListResponse(faqs);
    }

*/


    //faq 리스트 조회
    @Transactional
    public Page<Faq> getFaqList(Pageable pageable, String registerStart, String registerEnd, FaqType faqType, String noticeStart, String noticeEnd, String categoryName, String registerUserName, String faqTitle) {
        LocalDate registerStartDate = null;
        LocalDate registerEndDate = null;

        if(registerStart != null && registerEnd != null) {
            registerStartDate = LocalDate.parse(registerStart, DateTimeFormatter.ISO_DATE);
            registerEndDate = LocalDate.parse(registerEnd, DateTimeFormatter.ISO_DATE);
        }
        LocalDate noticeStartDate = null;
        LocalDate noticeEndDate = null;

        if(noticeStart != null && noticeEnd != null) {
            noticeStartDate = LocalDate.parse(noticeStart, DateTimeFormatter.ISO_DATE);
            noticeEndDate = LocalDate.parse(noticeEnd, DateTimeFormatter.ISO_DATE);
        }

        return faqRepository.getFaqList(pageable, categoryName, registerStartDate, registerEndDate, noticeStartDate, noticeEndDate, faqType, registerUserName, faqTitle);
    }

    /** * * * * * * * * * * **
     *                       *
     * faq 리스트 faqType조회  *
     *                       *
     * * * * * * * * * * * * */
    /**
    @Transactional
    public Object searchFaqTypeList(FaqType faqType) {
        //public Object searchFaqList(String categoryName, LocalDateTime registerDateStart, LocalDateTime registerDateEnd, LocalDateTime noticeDateStart, LocalDateTime noticeDateEnd, FaqType state, String registerUser, String title) {

        List<Faq> faqs = null;

        // 전체리스트 조회
        faqs = faqRepository.findByFaqType(faqType);

        return FaqMapper.toSearchFaqListResponse(faqs);
    }
    */


    /** * * * * * * *
     *              *
     * faq 상세 조회  *
     *              *
     * * * * * * * **/
    @Transactional
    public Object searchFaqDetail(Long faqId) {

        Faq faq = faqRepository.findById(faqId).orElseThrow(() -> {
            log.error("searchFaqDetail Exception : [존재하지 않는 Faq ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        return FaqMapper.toSearchFaqDetailResponse(faq);
    }


    /** * * * * **
     *           *
     *  faq 수정  *
     *           *
     * * * * * * */
    @Transactional
    public FaqDto updateFaq(Long faqId, FaqRequest.UpdateFaqRequest updateFaqRequest) {

        // Null 일 경우, ERROR 발생
        Faq faq = faqRepository.findById(faqId).orElseThrow(() -> {
            log.error("updateFaq Exception : [존재하지 않는 Faq ID]");
            return new InternWorkException.dataUpdateException();
        });

        // Null 이 아니면
        faq.updateFaqType(updateFaqRequest.getFaqType() != null ? updateFaqRequest.getFaqType() : faq.getFaqType());
        faq.updateContent(updateFaqRequest.getContent() != null ? updateFaqRequest.getContent() : faq.getContent());
        faq.updateTitle(updateFaqRequest.getFaqTitle() != null ? updateFaqRequest.getFaqTitle() : faq.getFaqTitle());
        faq.updateUpdateDate(updateFaqRequest.getUpdateDate() != null ? updateFaqRequest.getUpdateDate() : faq.getUpdateDate());
        faq.updateUpdateReason(updateFaqRequest.getUpdateReason() != null ? updateFaqRequest.getUpdateReason() : faq.getUpdateReason());
        faq.updateUpdateUser(updateFaqRequest.getUpdateUser() != null ? updateFaqRequest.getUpdateUser() : faq.getUpdateUser());

        try {
            faqRepository.save(faq);
        } catch (Exception e) {
            log.error("updateFaq Exception : {}", e.getMessage());
            throw new InternWorkException.dataUpdateException();
        }

        return FaqMapper.toUpdateFaqResponse(faq);
    }


    /** * * * * **
     *           *
     *  faq 삭제  *
     *           *
     * * * * * * */
    @Transactional
    public Response deleteFaq(Long faqId) {
        Optional<Faq> faq = faqRepository.findById(faqId);

        if (faq.isPresent()) {
            try {
                faqRepository.deleteById(faqId);
            } catch (Exception e) {
                throw new InternWorkException.dataDeleteException();
        }
        return Response.ok();
    }

    throw new InternWorkException.dataNotFoundException();
    }



}
