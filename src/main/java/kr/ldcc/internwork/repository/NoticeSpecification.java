package kr.ldcc.internwork.repository;

import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.entity.Notice;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class NoticeSpecification {
    public static Specification<Notice> betweenRegisterDate(LocalDateTime registerDateStart, LocalDateTime registerDateEnd) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("registerDate"), registerDateStart, registerDateEnd);
    }

    public static Specification<Notice> betweenNoticeDate(LocalDateTime noticeDateStart, LocalDateTime noticeDateEnd) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("registerDate"), noticeDateStart, noticeDateEnd);
    }

    public static Specification<Notice> equalState(NoticeType state) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"), state);
    }

    public static Specification<Notice> equalTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("title"), title);
    }

    public static Specification<Notice> equalRegisterUser(String registerUserName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("registerUser").get("name"), registerUserName);
    }
}
