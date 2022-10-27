package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long>, FaqRepositorySupport {

    List<Faq> findAllById(Long faqId);

    Faq findByFaqTitle(String faqTitle);

    List<Faq> findByFaqType(FaqType faqType);


}
