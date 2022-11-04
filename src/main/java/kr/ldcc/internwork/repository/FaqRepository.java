package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.model.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FaqRepository extends JpaRepository<Faq, Long>, FaqRepositorySupport {

    Faq findByFaqTitle(String faqTitle);

    Optional<Faq> findByCategoryId(Long categoryId);



}
