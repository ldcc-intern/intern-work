package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositorySupport {
}
