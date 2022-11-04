package kr.ldcc.internwork.service;


import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.dto.FaqDto;
import kr.ldcc.internwork.model.dto.request.FaqRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Category;
import kr.ldcc.internwork.model.entity.Faq;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.model.mapper.FaqMapper;
import kr.ldcc.internwork.repository.CategoryRepository;
import kr.ldcc.internwork.repository.FaqRepository;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    /** * * * * *
     *          *
     * faq 등록  *
     *          *
     * * * * * **/
    @Transactional
    public FaqDto.RegisterFaqResponse registerFaq(FaqRequest.RegisterFaqRequest registerFaqRequest) {

        User registerUser = userRepository.findById(registerFaqRequest.getRegisterUserId()).orElseThrow(() -> {
            log.error("registerFaq Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });

        Category category = categoryRepository.findById(registerFaqRequest.getCategoryId()).orElseThrow(()->{
            log.error("registerFaq Exception : [존재하지 않는 Category ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });

        LocalDateTime noticeDate = LocalDateTime.parse(registerFaqRequest.getNoticeDate() + " " +registerFaqRequest.getNoticeTime(), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));

        Faq faq = Faq.builder()
                .category(category)
                .faqType(registerFaqRequest.getFaqType())
                .faqTitle(registerFaqRequest.getFaqTitle())
                .registerUser(registerUser)
                .content(registerFaqRequest.getContent())
                .authInfo(registerFaqRequest.getAuthInfo())
                .noticeDate(noticeDate)
                .build();

        // faq 정보 저장
        faqRepository.save(faq);


        return FaqMapper.toRegisterFaqResponse(faq);
    }


    /** * * * * * * * **
     *                 *
     *  faq 리스트 조회  *
     *                 *
     * * * * * * * * * */
    @Transactional
    public Page<Faq> getFaqList(Pageable pageable, String registerStart, String registerEnd, FaqType faqType, String noticeStart, String noticeEnd, String categoryName, String registerUserName, String faqTitle) {
        LocalDate registerStartDate = null;
        LocalDate registerEndDate = null;

        if(registerStart != null && registerEnd != null) {
            registerStartDate = LocalDate.parse(registerStart, DateTimeFormatter.ISO_DATE);
            registerEndDate = LocalDate.parse(registerEnd, DateTimeFormatter.ISO_DATE) ;
        }
        LocalDate noticeStartDate = null;
        LocalDate noticeEndDate = null;

        if(noticeStart != null && noticeEnd != null) {
            noticeStartDate = LocalDate.parse(noticeStart, DateTimeFormatter.ISO_DATE);
            noticeEndDate = LocalDate.parse(noticeEnd, DateTimeFormatter.ISO_DATE);
        }



        return faqRepository.getFaqList(pageable, categoryName, registerStartDate, registerEndDate, noticeStartDate, noticeEndDate, faqType, registerUserName, faqTitle);
    }


    /** * * * * * * *
     *              *
     * faq 상세 조회  *
     *              *
     * * * * * * * **/
    @Transactional
    public Faq searchFaqDetail(Long faqId) {

        Faq faq = faqRepository.findById(faqId).orElseThrow(() -> {
            log.error("searchFaqDetail Exception : [존재하지 않는 Faq ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });

        return faq;
    }


    /** * * * * **
     *           *
     *  faq 수정  *
     *           *
     * * * * * * */
    @Transactional
    public Faq updateFaq(Long faqId, FaqRequest.UpdateFaqRequest updateFaqRequest) {

        // Null 일 경우, ERROR 발생
        Faq faq = faqRepository.findById(faqId).orElseThrow(() -> {
            log.error("updateFaq Exception : [존재하지 않는 Faq ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataUpdateException();
        });

        User user = userRepository.findById(updateFaqRequest.getUpdateUserId()).orElseThrow(() -> {
            log.error("updateFaq Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });

        Category category = categoryRepository.findById(updateFaqRequest.getCategoryId()).orElseThrow(() -> {
            log.error("updateFaq Exception : [존재하지 않는 Category ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });

        if (updateFaqRequest.getNoticeDate() != null && updateFaqRequest.getNoticeTime() != null){
            String date = updateFaqRequest.getNoticeDate();
            String time = updateFaqRequest.getNoticeTime();
            LocalDateTime noticeDate = LocalDateTime.parse(date +" "+ time, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
            faq.updateNoticeDate(noticeDate);
        }

        // Null 이 아니면
        faq.updateFaqType(updateFaqRequest.getFaqType() != null ? updateFaqRequest.getFaqType() : faq.getFaqType());
        faq.updateContent(updateFaqRequest.getContent() != null ? updateFaqRequest.getContent() : faq.getContent());
        faq.updateTitle(updateFaqRequest.getFaqTitle() != null ? updateFaqRequest.getFaqTitle() : faq.getFaqTitle());
        faq.updateUpdateReason(updateFaqRequest.getUpdateReason() != null ? updateFaqRequest.getUpdateReason() : faq.getUpdateReason());
        faq.updateUpdateUser(user);
        faq.updateCategory(category);

        try {
            faqRepository.save(faq);
        } catch (Exception e) {
            log.error("updateFaq Exception : {}", e.getMessage());
            throw new InternWorkException.dataUpdateException();
        }

        return faq;
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
