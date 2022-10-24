package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long>, JpaSpecificationExecutor<Notice> {
    @Modifying
    @Query("update Notice n set n.view = n.view + 1 where n.id = :id")
    int updateView(Long id);
}
