package kr.ldcc.internwork.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.entity.Faq;
import kr.ldcc.internwork.model.entity.QFaq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static kr.ldcc.internwork.model.entity.QFaq.faq;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
class FaqRepositorySupportImplTest {

    private final JPAQueryFactory queryFactory;

    private FaqRepository faqRepository;

    FaqRepositorySupportImplTest(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Test
    public void testQuerydsl() {
        Faq getFaqList = queryFactory
                .select(faq)
                .from(faq)
                .where(faq.faqType.eq(FaqType.SHOW))
                .fetchOne();
    }


}