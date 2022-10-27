package kr.ldcc.internwork.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static kr.ldcc.internwork.model.entity.QNotice.notice;
import static kr.ldcc.internwork.model.entity.QUser.user;

@RequiredArgsConstructor
public class NoticeRepositorySupportImpl implements NoticeRepositorySupport {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Notice> getNoticeList(Pageable pageable, LocalDateTime registerDateStart, LocalDateTime registerDateEnd, NoticeType state, LocalDateTime noticeDateStart, LocalDateTime noticeDateEnd, String userName, String title) {
        List<Notice> content = queryFactory
                .selectFrom(notice)
                .leftJoin(notice.registerUser, user)
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
                .select(notice.count())
                .from(notice)
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
        return title == null ? null : notice.title.eq(title);
    }

    private BooleanExpression findByUserName(String userName) {
        return userName == null ? null : notice.registerUser.name.eq(userName);
    }

    private BooleanExpression betweenNoticeDate(LocalDateTime noticeDateStart, LocalDateTime noticeDateEnd) {
        return noticeDateStart == null && noticeDateEnd == null ? null : notice.noticeDate.between(noticeDateStart, noticeDateEnd);
    }

    private BooleanExpression findByState(NoticeType state) {
        return notice.state.eq(state);
    }

    private BooleanExpression betweenRegisterDate(LocalDateTime registerDateStart, LocalDateTime registerDateEnd) {
        return registerDateStart == null && registerDateEnd == null ? null : notice.registerDate.between(registerDateStart, registerDateEnd);
    }
}
