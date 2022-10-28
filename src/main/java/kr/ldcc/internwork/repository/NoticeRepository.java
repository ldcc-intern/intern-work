package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NoticeRepository extends JpaRepository<Notice, Long>, QuerydslPredicateExecutor<Notice> {
    @Modifying
    @Query("update Notice n set n.view = n.view + 1 where n.id = :id")
    Integer updateView(Long id);
}
