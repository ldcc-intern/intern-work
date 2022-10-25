package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface NoticeRepositorySupport {
    Page<Notice> getNoticeList(
            Pageable pageable,
            LocalDate registerDateStart,
            LocalDate registerDateEnd,
            NoticeType state,
            LocalDate noticeDateStart,
            LocalDate noticeDateEnd,
            String userName,
            String title
    );
}
