package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException.DataDeleteException;
import kr.ldcc.internwork.common.exception.InternWorkException.DataNotFoundException;
import kr.ldcc.internwork.common.exception.InternWorkException.DataSaveException;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.dto.FaqDto;
import kr.ldcc.internwork.model.dto.FaqDto.FaqDetail;
import kr.ldcc.internwork.model.dto.FaqDto.FaqPage;
import kr.ldcc.internwork.model.dto.request.FaqRequest;
import kr.ldcc.internwork.model.entity.Category;
import kr.ldcc.internwork.model.entity.Faq;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.repository.CategoryRepository;
import kr.ldcc.internwork.repository.FaqRepository;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FaqService {
    private final FaqRepository faqRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    /**
     * * * * *
     * *
     * faq 등록  *
     * *
     * * * * *
     **/
    public void registerFaq(FaqRequest.RegisterFaqRequest registerFaqRequest) {
        User registerUser = userRepository.findById(registerFaqRequest.getRegisterUserId()).orElseThrow(() -> {
            log.error("registerFaq Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        // 카테고리 이름으로 카테고리 있는지 찾기
        Optional<Category> categoryCheck = Optional.ofNullable(categoryRepository.findByCategoryName(registerFaqRequest.getCategoryName()));
        if (!categoryCheck.isPresent()) {
            log.error("registerFaq Exception : [존재하지 않는 Category ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            throw new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        }
        LocalDateTime noticeDate = LocalDateTime.parse(registerFaqRequest.getNoticeDate() + " " + registerFaqRequest.getNoticeTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Faq faq = Faq.builder()
                .category(categoryRepository.findByCategoryName(registerFaqRequest.getCategoryName()))
                .faqType(registerFaqRequest.getFaqType())
                .faqTitle(registerFaqRequest.getFaqTitle())
                .registerUser(registerUser)
                .content(registerFaqRequest.getContent())
                .noticeDate(noticeDate)
                .build();
        // faq 정보 저장
        try {
            faqRepository.save(faq);
        } catch (Exception e) {
            log.error("registerFaq Exception :{}", e.getMessage());
            throw new DataSaveException(ExceptionCode.DATA_SAVE_EXCEPTION);
        }
    }

    /**
     * * * * * * * **
     * *
     * faq 리스트 조회  *
     * *
     * * * * * * * * *
     */
    public FaqPage getFaqList(Pageable pageable, String registerStart, String registerEnd, FaqType faqType, String noticeStart, String noticeEnd, String categoryName, String registerUserName, String faqTitle) {
        LocalDate registerStartDate = null;
        LocalDate registerEndDate = null;
        if (registerStart != null && registerEnd != null) {
            registerStartDate = LocalDate.parse(registerStart, DateTimeFormatter.ISO_DATE);
            registerEndDate = LocalDate.parse(registerEnd, DateTimeFormatter.ISO_DATE);
        }
        LocalDate noticeStartDate = null;
        LocalDate noticeEndDate = null;
        if (noticeStart != null && noticeEnd != null) {
            noticeStartDate = LocalDate.parse(noticeStart, DateTimeFormatter.ISO_DATE);
            noticeEndDate = LocalDate.parse(noticeEnd, DateTimeFormatter.ISO_DATE);
        }
        return FaqDto.buildFaqPage(faqRepository.getFaqList(pageable, categoryName, registerStartDate, registerEndDate, noticeStartDate, noticeEndDate, faqType, registerUserName, faqTitle));
    }

    /**
     * * * * * * *
     * *
     * faq 상세 조회  *
     * *
     * * * * * * *
     **/
    public FaqDetail searchFaqDetail(Long faqId) {
        return FaqDto.buildFaqDetail(faqRepository.findById(faqId).orElseThrow(() -> {
            log.error("searchFaqDetail Exception : [존재하지 않는 Faq ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        }));
    }

    /**
     * * * * **
     * *
     * faq 수정  *
     * *
     * * * * * *
     */
    public void updateFaq(Long faqId, FaqRequest.UpdateFaqRequest updateFaqRequest) {
        // Null 일 경우, ERROR 발생
        Faq faq = faqRepository.findById(faqId).orElseThrow(() -> {
            log.error("updateFaq Exception : [존재하지 않는 Faq ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        User user = userRepository.findById(updateFaqRequest.getUpdateUserId()).orElseThrow(() -> {
            log.error("updateFaq Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        if (updateFaqRequest.getCategoryName() != null) {
            Optional<Category> categoryCheck = Optional.ofNullable(categoryRepository.findByCategoryName(updateFaqRequest.getCategoryName()));
            if (!categoryCheck.isPresent()) {
                log.error("updateFaq Exception : [존재하지 않는 Category ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
                throw new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            }
            faq.updateCategory(categoryRepository.findByCategoryName(updateFaqRequest.getCategoryName()));
        }
        if (updateFaqRequest.getNoticeDate() != null && updateFaqRequest.getNoticeTime() != null) {
            String date = updateFaqRequest.getNoticeDate();
            String time = updateFaqRequest.getNoticeTime();
            LocalDateTime noticeDate = LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            faq.updateNoticeDate(noticeDate);
        }
        // Null 이 아니면
        faq.updateFaqType(updateFaqRequest.getFaqType() != null ? updateFaqRequest.getFaqType() : faq.getFaqType());
        faq.updateContent(updateFaqRequest.getContent() != null ? updateFaqRequest.getContent() : faq.getContent());
        faq.updateTitle(updateFaqRequest.getFaqTitle() != null ? updateFaqRequest.getFaqTitle() : faq.getFaqTitle());
        faq.updateUpdateReason(updateFaqRequest.getUpdateReason() != null ? updateFaqRequest.getUpdateReason() : faq.getUpdateReason());
        faq.updateUpdateUser(user);
    }

    /**
     * * * * **
     * *
     * faq 삭제  *
     * *
     * * * * * *
     */
    public void deleteFaq(Long faqId) {
        Optional<Faq> faq = faqRepository.findById(faqId);
        if (faq.isPresent()) {
            try {
                faqRepository.deleteById(faqId);
            } catch (Exception e) {
                throw new DataDeleteException(ExceptionCode.DATA_DELETE_EXCEPTION);
            }
            return;
        }
        throw new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
    }
}
