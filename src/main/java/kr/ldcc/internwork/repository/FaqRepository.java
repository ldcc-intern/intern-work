package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.model.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}
