package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.dto.request.FaqRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.FaqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/faq")
@Slf4j
public class FaqController {
    @Autowired
    private FaqService faqService;

    /**
     * * * * * *
     * *
     * faq 등록   *
     * *
     * * * * * *
     **/
    @PostMapping
    public Response registerFaq(@RequestBody @Valid FaqRequest.RegisterFaqRequest registerFaqRequest) {
        log.info("[registerFaq]");
        faqService.registerFaq(registerFaqRequest);
        return Response.ok();
    }

    /**
     * * * * * * * * * * *
     * *
     * faq 리스트 조건 조회   *
     * *
     * * * * * * * * * * *
     */
    // 검색조건
    // categoryName, registerStartDate, registerEndDate, noticeStartDate, noticeEndDate, faqType, registerUserName, faqTitle
    @GetMapping
    public Response searchFaqList(
            @PageableDefault(size = 25) Pageable pageable,
            @RequestParam(required = false) FaqType faqType,
            @RequestParam(required = false) String registerStart,
            @RequestParam(required = false) String registerEnd,
            @RequestParam(required = false) String noticeStart,
            @RequestParam(required = false) String noticeEnd,
            @RequestParam(required = false) String faqTitle,
            @RequestParam(required = false) String registerUserName,
            @RequestParam(required = false) String categoryName
    ) {
        log.info("[searchFaqList]");
        return Response.ok().setData(faqService.getFaqList(pageable, registerStart, registerEnd, faqType, noticeStart, noticeEnd, categoryName, registerUserName, faqTitle));
    }

    // faq 상세 조회
    @GetMapping("/{faqId}")
    public Response searchFaqDetail(@PathVariable("faqId") Long faqId) {
        log.info("[searchFaqDetail]");
        return Response.ok().setData(faqService.searchFaqDetail(faqId));
    }

    // faq 수정
    @PutMapping("/{faqId}")
    public Response updateFaq(@PathVariable("faqId") Long faqId, @RequestBody @Valid FaqRequest.UpdateFaqRequest updateFaqRequest) {
        log.info("[updateFaq]");
        faqService.updateFaq(faqId, updateFaqRequest);
        return Response.ok();
    }

    // faq 삭제
    @DeleteMapping("/{faqId}")
    public Response deleteFaq(@PathVariable("faqId") Long faqId) {
        log.info("[deleteFaq]");
        faqService.deleteFaq(faqId);
        return Response.ok();
    }
}
