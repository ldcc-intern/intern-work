package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.entity.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface FaqRepositorySupport {
    Page<Faq> getFaqList(
            Pageable pageable,
            String categoryName,
            LocalDate registerStartDate,
            LocalDate registerEndDate,
            LocalDate noticeStartDate,
            LocalDate noticeEndDate,
            FaqType faqType,
            String registerUserName,
            String faqTitle
    );
}
