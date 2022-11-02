package kr.ldcc.internwork.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static kr.ldcc.internwork.model.entity.QNotice.notice;

@RequiredArgsConstructor
public class NoticeRepositorySupportImpl implements NoticeRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Notice> getNoticeList(LocalDate registerStart, LocalDate registerEnd, NoticeType state, LocalDate noticeDateStart, LocalDate noticeDateEnd, String registerUserName, String title, Pageable pageable) {
        List<Notice> content = jpaQueryFactory
                .selectFrom(notice)
                .where(
                        betweenRegisterDate(registerStart, registerEnd),
                        findByState(state),
                        betweenNoticeDate(noticeDateStart, noticeDateEnd),
                        findByRegisterUserName(registerUserName),
                        findByTitle(title)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        int total = jpaQueryFactory
                .selectFrom(notice)
                .where(
                        betweenRegisterDate(registerStart, registerEnd),
                        findByState(state),
                        betweenNoticeDate(noticeDateStart, noticeDateEnd),
                        findByRegisterUserName(registerUserName),
                        findByTitle(title)
                )
                .fetch().size();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression betweenRegisterDate(LocalDate registerStart, LocalDate registerEnd) {
        if (registerStart != null && registerEnd != null) {
            return notice.registerDate.between(registerStart.atTime(0, 0), registerEnd.atTime(23, 59));
        }
        return null;
    }

    private BooleanExpression findByState(NoticeType state) {
        if (state != null) {
            return notice.state.eq(state);
        }
        return null;
    }

    private BooleanExpression betweenNoticeDate(LocalDate noticeDateStart, LocalDate noticeDateEnd) {
        if (noticeDateStart != null && noticeDateEnd != null) {
            return notice.noticeDate.between(noticeDateStart.atTime(0, 0), noticeDateEnd.atTime(23, 59));
        }
        return null;
    }

    private BooleanExpression findByRegisterUserName(String registerUserName) {
        if (registerUserName != null) {
            return notice.registerUser.name.eq(registerUserName);
        }
        return null;
    }

    private BooleanExpression findByTitle(String title) {
        if (title != null) {
            return notice.title.eq(title);
        }
        return null;
    }
}
