package kr.ldcc.internwork.controller;


import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.common.types.validation.Enum;
import kr.ldcc.internwork.model.dto.FaqDto;
import kr.ldcc.internwork.model.dto.request.FaqRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Faq;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.model.mapper.FaqMapper;
import kr.ldcc.internwork.service.FaqService;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;



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
        return Response.ok().setData(faqService.registerFaq(registerFaqRequest));

    }


    /** * * * * * * * * * * *
     *                      *
     *  faq 리스트 조건 조회   *
     *                      *
     * * * * * * * * * * * */

    // 검색조건
    // categoryName, registerStartDate, registerEndDate, noticeStartDate, noticeEndDate, faqType, registerUserName, faqTitle

    @GetMapping("/faq")
    public Response searchFaqList(
            Pageable pageable,
            @RequestParam(required = false) @Enum(enumClass = FaqType.class) FaqType faqType,
            @RequestParam(required = false) String registerStart,
            @RequestParam(required = false) String registerEnd,
            @RequestParam(required = false) String noticeStart,
            @RequestParam(required = false) String noticeEnd,
            @RequestParam(required = false) String faqTitle,
            @RequestParam(required = false) String registerUserName,
            @RequestParam(required = false) String categoryName

            ) {

        return Response.ok().setData(FaqMapper.toSearchFaqListResponse(faqService.getFaqList(pageable, registerStart, registerEnd, faqType, noticeStart, noticeEnd, categoryName, registerUserName, faqTitle)));

    }


    // faq 상세 조회
    @GetMapping("/faq/{faqId}")
    public Response searchFaqDetail(@PathVariable("faqId") Long faqId) {

        return Response.ok().setData(FaqMapper.toSearchFaqDetailResponse(faqService.searchFaqDetail(faqId)));
    }

    // faq 수정
    @PutMapping("/faq/{faqId}")
    public Response updateFaq(@PathVariable("faqId") Long faqId, @RequestBody @Valid FaqRequest.UpdateFaqRequest updateFaqRequest) {

        return Response.ok().setData(FaqMapper.toUpdateFaqResponse(faqService.updateFaq(faqId, updateFaqRequest)));
    }

    // faq 삭제
    @DeleteMapping("/faq/{faqId}")
    public Response deleteFaq(@PathVariable("faqId") Long faqId){
        return faqService.deleteFaq(faqId);
    }



}
