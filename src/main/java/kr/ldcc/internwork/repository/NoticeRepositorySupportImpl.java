package kr.ldcc.internwork.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.entity.Notice;
import kr.ldcc.internwork.model.entity.QNotice;
import kr.ldcc.internwork.model.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public class NoticeRepositorySupportImpl implements NoticeRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public NoticeRepositorySupportImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QNotice qNotice = QNotice.notice;
    QUser qUser = QUser.user;

    @Override
    public Page<Notice> getNoticeList(Pageable pageable, LocalDate registerDateStart, LocalDate registerDateEnd, NoticeType state, LocalDate noticeDateStart, LocalDate noticeDateEnd, String userName, String title) {
        List<Notice> content = queryFactory
                .selectFrom(qNotice)
                .leftJoin(qNotice.registerUser, qUser)
                .fetchJoin()
                .where(
                        betweenRegisterDate(registerDateStart, registerDateEnd),
                        findByState(state),
                        betweenNoticeDate(noticeDateStart, noticeDateEnd),
                        findByUserName(userName),
                        findByTitle(title)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = queryFactory
                .select(qNotice.count())
                .from(qNotice)
                .where(
                        betweenRegisterDate(registerDateStart, registerDateEnd),
                        findByState(state),
                        betweenNoticeDate(noticeDateStart, noticeDateEnd),
                        findByUserName(userName),
                        findByTitle(title)
                )
                .fetchOne();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression findByTitle(String title) {
        if (title == null) {
            return null;
        }
        return qNotice.title.eq(title);
    }

    private BooleanExpression findByUserName(String userName) {
        if (userName == null) {
            return null;
        }
        return qNotice.registerUser.name.eq(userName);
    }

    private BooleanExpression betweenNoticeDate(LocalDate noticeDateStart, LocalDate noticeDateEnd) {
        if (noticeDateStart == null && noticeDateEnd == null) {
            return null;
        }
        return qNotice.noticeDate.between(noticeDateStart.atTime(0, 0), noticeDateEnd.atTime(23, 59));
    }

    private BooleanExpression findByState(NoticeType state) {
        return qNotice.state.eq(state);
    }

    private BooleanExpression betweenRegisterDate(LocalDate registerDateStart, LocalDate registerDateEnd) {
        if (registerDateStart == null && registerDateEnd == null) {
            return null;
        }
        return qNotice.registerDate.between(registerDateStart.atTime(0, 0), registerDateEnd.atTime(23, 59));
    }
}
