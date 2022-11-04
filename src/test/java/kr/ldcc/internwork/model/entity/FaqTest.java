package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.controller.FaqController;
import kr.ldcc.internwork.model.dto.request.FaqRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.repository.FaqRepository;
import kr.ldcc.internwork.repository.UserRepository;
import kr.ldcc.internwork.service.FaqService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
class FaqTest {

    @Autowired FaqRepository faqRepository;

    @Autowired FaqController faqController;

    @Autowired FaqService faqService;

    @Autowired UserRepository userRepository;

    @BeforeEach
    public void before() {
        Faq faq = Faq.builder()
                .faqType(FaqType.RESERVE)
                .faqTitle("Test")
                .content("Test")
                .noticeDate(LocalDateTime.now())
                .authInfo("Test")
                .registerUser(userRepository.findByName("name"))
                .build();
        faqRepository.save(faq);
    }

    @Test
    public void 자주묻는질문등록() throws Exception {
        FaqRequest.RegisterFaqRequest registerFaqRequest = new FaqRequest.RegisterFaqRequest();

        registerFaqRequest.setFaqType(FaqType.RESERVE);
        registerFaqRequest.setFaqTitle("제목");
        registerFaqRequest.setContent("내용은");
        registerFaqRequest.setAuthInfo("인증");

        Response faq = faqController.registerFaq(registerFaqRequest);

        assertEquals(faqRepository.findByFaqTitle("제목").getAuthInfo(),"인증");
    }

    @Test
    public void 자주묻는질문리스트조회() throws Exception {
        List<Faq> faqList = faqRepository.findAll();
        assertNotNull(faqList.size());
    }

    @Test
    public void 자주묻는질문상세조회() throws Exception {
        Faq findFaq = faqRepository.findByFaqTitle("제목수정");

        Optional<Faq> faq = faqRepository.findById(findFaq.getId());

        assertEquals(faq.get().getFaqTitle(), findFaq.getFaqTitle());
    }


    @Test
    public void 자주묻는질문제목수정() throws Exception {
        Faq findFaq = faqRepository.findByFaqTitle("제목");

        FaqRequest.UpdateFaqRequest updateFaqRequest = new FaqRequest.UpdateFaqRequest();
        updateFaqRequest.setFaqTitle("제목수정확인");
        updateFaqRequest.setFaqType(FaqType.SHOW);
        updateFaqRequest.setUpdateReason("수정이유입니다");
        updateFaqRequest.setUpdateUserId(2L);

        faqController.updateFaq(findFaq.getId(), updateFaqRequest);

        Optional<Faq> byId = faqRepository.findById(findFaq.getId());

        assertEquals(byId.get().getFaqTitle(), updateFaqRequest.getFaqTitle());
    }

    @Test
    public void 자주묻는질문삭제() throws Exception {
        Faq findFaq = faqRepository.findByFaqTitle("제목수정확인");

        faqController.deleteFaq(findFaq.getId());

        Optional<Faq> faq = faqRepository.findById(findFaq.getId());

        // False 여야 한다.
        assertFalse(faq.isPresent());
    }
}