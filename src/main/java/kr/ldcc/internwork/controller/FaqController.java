package kr.ldcc.internwork.controller;


import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.dto.request.FaqRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Faq;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.service.FaqService;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class FaqController {

    private final FaqService faqService;

    @Autowired
    public FaqController(FaqService faqService) {
        this.faqService = faqService;

    }

    /** * * * * * *
     *            *
     *  faq 등록   *
     *            *
     * * * * * * **/
    @PostMapping("/faq")
    public Response registerFaq(@RequestBody @Valid FaqRequest.RegisterFaqRequest registerFaqRequest) {
        return faqService.registerFaq(registerFaqRequest);

    }

    /** * * * * * * * * *
     *                  *
     *  faq 리스트 조회   *
     *                  *
     * * * * * * * * * **/
    @GetMapping("/faq")
    public Object searchFaqList(
            @RequestParam(value = "categoryName") String categoryName,
            @RequestParam(value = "registerDateStart") LocalDateTime registerDateStart,
            @RequestParam(value = "registerDateEnd") LocalDateTime registerDateEnd,
            @RequestParam(value = "noticeDateStart") LocalDateTime noticeDateStart,
            @RequestParam(value = "noticeDateEnd") LocalDateTime noticeDateEnd,
            @RequestParam(value = "state") FaqType state,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "registerUserName") String registerUserName
            ) {

        return faqService.searchFaqList(categoryName, registerDateStart,
                registerDateEnd, noticeDateStart, noticeDateEnd, state,
                registerUserName, title);

    }


    // faq 상세 조회
    @GetMapping("/faq/{faqId}")
    public Response searchFaqDetail(@PathVariable("faqId") Long faqId) {

        return faqService.searchFaqDetail(faqId);
    }

    // faq 수정
    @PutMapping("/faq/{faqId}")
    public Response updateFaq(@PathVariable("faqId") Long faqId, @RequestBody @Valid FaqRequest.UpdateFaqRequest updateFaqRequest) {
        return faqService.updateFaq(faqId, updateFaqRequest);
    }

    // faq 삭제
    @DeleteMapping("/faq/{faqId}")
    public Response deleteFaq(@PathVariable("faqId") Long faqId){
        return faqService.deleteFaq(faqId);
    }
}
