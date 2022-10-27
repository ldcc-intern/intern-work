package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface NoticeRepositorySupport {
    Page<Notice> getNoticeList(
            Pageable pageable,
            LocalDateTime registerDateStart,
            LocalDateTime registerDateEnd,
            NoticeType state,
            LocalDateTime noticeDateStart,
            LocalDateTime noticeDateEnd,
            String userName,
            String title
    );
}
