package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.controller.FaqController;
import kr.ldcc.internwork.model.dto.request.FaqRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.repository.FaqRepository;
import kr.ldcc.internwork.service.FaqService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FaqTest {

    @Autowired FaqRepository faqRepository;

    @Autowired FaqController faqController;

    @Autowired FaqService faqService;

    @BeforeEach
    public void before() {
        Faq faq = Faq.builder()
                .faqType(FaqType.RESERVE)
                .faqTitle("제목테스트")
                .content("Faq내용")
                .noticeDate(LocalDateTime.now())
                .build();
        faqRepository.save(faq);
    }

    @Test
    public void 자주묻는질문등록() throws Exception {
        FaqRequest.RegisterFaqRequest registerFaqRequest = new FaqRequest.RegisterFaqRequest();

        registerFaqRequest.setFaqType(FaqType.RESERVE);
        registerFaqRequest.setCategoryName("우리동네");
        registerFaqRequest.setFaqTitle("질문");
        registerFaqRequest.setContent("내용은");
        registerFaqRequest.setRegisterDate(LocalDateTime.now());

        Response faq = faqController.registerFaq(registerFaqRequest);

        assertEquals(faqRepository.findByFaqTitle("질문").getAuthInfo(),"인증");
    }

    @Test
    public void 자주묻는질문리스트조회() throws Exception {
        List<Faq> faqList = faqRepository.findAll();
        assertNotNull(faqList.size());
    }

    @Test
    public void 자주묻는질문상세조회() throws Exception {
        Faq findFaq = faqRepository.findByFaqTitle("제목테스트");

        Optional<Faq> faq = faqRepository.findById(findFaq.getId());

        assertEquals(faq.get().getFaqTitle(), findFaq.getFaqTitle());
    }


    @Test
    public void 자주묻는질문제목수정() throws Exception {
        Faq findFaq = faqRepository.findByFaqTitle("안녕하세요");

        FaqRequest.UpdateFaqRequest updateFaqRequest = new FaqRequest.UpdateFaqRequest();
        updateFaqRequest.setFaqTitle("제목수정확인");

        faqController.updateFaq(findFaq.getId(), updateFaqRequest);

        Optional<Faq> byId = faqRepository.findById(findFaq.getId());

        assertEquals(byId.get().getFaqTitle(), updateFaqRequest.getFaqTitle());
    }

    @Test
    public void 자주묻는질문삭제() throws Exception {
        Faq findFaq = faqRepository.findByFaqTitle("안녕하세요");

        faqController.deleteFaq(findFaq.getId());

        Optional<Faq> faq = faqRepository.findById(findFaq.getId());

        // false
        assertFalse(faq.isPresent());
    }
}