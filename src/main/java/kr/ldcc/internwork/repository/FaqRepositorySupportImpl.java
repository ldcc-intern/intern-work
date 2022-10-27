package kr.ldcc.internwork.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.entity.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static kr.ldcc.internwork.model.entity.QFaq.faq;
import static kr.ldcc.internwork.model.entity.QUser.user;


public class FaqRepositorySupportImpl implements FaqRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public FaqRepositorySupportImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    //QFaq qFaq = QFaq.faq;

    // qUser = QUser.user;

    @Override
    public Page<Faq> getFaqList(Pageable pageable, String categoryName, LocalDate registerStartDate, LocalDate registerEndDate, LocalDate noticeStartDate, LocalDate noticeEndDate, FaqType faqType, String registerUserName, String title) {


        List<Faq> content = queryFactory
                .selectFrom(faq)
                .leftJoin(faq.registerUser, user)
                .fetchJoin()
                .where(
                        betweenRegisterDate(registerStartDate, registerEndDate),
                        findByFaqType(faqType),
                        betweenNoticeDate(noticeStartDate, noticeEndDate),
                        findByCategoryName(categoryName),
                        findByRegisterUserName(registerUserName),
                        findByTitle(title)

                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(faq.count())
                .from(faq)
                .where(
                        betweenRegisterDate(registerStartDate, registerEndDate),
                        findByFaqType(faqType),
                        betweenNoticeDate(noticeStartDate, noticeEndDate),
                        findByCategoryName(categoryName),
                        findByRegisterUserName(registerUserName),
                        findByTitle(title)
                )
                .fetchOne();
        return new PageImpl<>(content, pageable, total);
    }


    private BooleanExpression findByTitle(String title) {
        if (title == null) {
            return null;
        }
        return faq.faqTitle.eq(title);
    }

    private BooleanExpression betweenNoticeDate(LocalDate noticeStartDate, LocalDate noticeEndDate) {
        if (noticeStartDate == null && noticeEndDate == null) {
            return null;
        }
        return faq.noticeDate.between(noticeStartDate.atTime(0, 0), noticeEndDate.atTime(23, 59));
    }

    private BooleanExpression betweenRegisterDate(LocalDate registerStartDate, LocalDate registerEndDate) {
        if (registerStartDate == null && registerEndDate == null) {
            return null;
        }
        return faq.noticeDate.between(registerEndDate.atTime(0, 0), registerEndDate.atTime(23, 59));
    }

    private BooleanExpression findByFaqType(FaqType faqType) {

        return faq.faqType.eq(faqType);
    }

    private BooleanExpression findByCategoryName(String categoryName) {
        return faq.categoryName.eq(categoryName);
    }


    private BooleanExpression findByRegisterUserName(String registerUserName) {
        return faq.registerUser.name.eq(registerUserName);
    }
}
