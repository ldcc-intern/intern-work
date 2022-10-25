package kr.ldcc.internwork.controller;


import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.dto.FaqDto;
import kr.ldcc.internwork.model.dto.request.FaqRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Faq;
import kr.ldcc.internwork.model.entity.User;
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
        return faqService.registerFaq(registerFaqRequest);

    }


    /** * * * * * * * * * **
     *                     *
     *  faq 리스트 Type 조회 *
     *                     *
     * * * * * * * * * * * */

    @GetMapping("/faq/{faqType}")
    public Response getFaqTypeList(@PathVariable("faqType") FaqType faqType) {
        return Response.ok().setData(faqService.searchFaqTypeList(faqType));
    }


    /** * * * * * * * * * **
     *                     *
     *  faq 리스트 전체 조회  *
     *                     *
     * * * * * * * * * * * */

    @GetMapping("/faq")
    public Response searchFaqAllList() {
        return Response.ok().setData(faqService.searchFaqAllList());
    }



    /** * * * * * * * * * * *
     *                      *
     *  faq 리스트 조건 조회   *
     *                      *
     * * * * * * * * * * * */

    /**
    @GetMapping("/faq")
    public Object searchFaqList(
            @RequestParam(value = "categoryName") String categoryName,
            @RequestParam(value = "registerDateStart") LocalDateTime registerDateStart,
            @RequestParam(value = "registerDateEnd") LocalDateTime registerDateEnd,
            @RequestParam(value = "noticeDateStart") LocalDateTime noticeDateStart,
            @RequestParam(value = "noticeDateEnd") LocalDateTime noticeDateEnd,
            @RequestParam(value = "faqType") FaqType faqType,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "registerUserName") String registerUserName
            ) {

        return faqService.searchFaqList(categoryName, registerDateStart,
                registerDateEnd, noticeDateStart, noticeDateEnd, faqType,
                registerUserName, title);

    }

    */


    // faq 상세 조회
    @GetMapping("/faq/{faqId}")
    public Response searchFaqDetail(@PathVariable("faqId") Long faqId) {

        return Response.ok().setData(faqService.searchFaqDetail(faqId));
    }

    // faq 수정
    @PutMapping("/faq/{faqId}")
    public Response updateFaq(@PathVariable("faqId") Long faqId, @RequestBody @Valid FaqRequest.UpdateFaqRequest updateFaqRequest) {

        Optional<FaqDto> FaqDto = Optional.ofNullable(faqService.updateFaq(faqId, updateFaqRequest));

        if (FaqDto.isPresent()) {
            // Response 설정
            return Response.ok();
        }


        return Response.dataNotFoundException();
    }

    // faq 삭제
    @DeleteMapping("/faq/{faqId}")
    public Response deleteFaq(@PathVariable("faqId") Long faqId){
        return faqService.deleteFaq(faqId);
    }



}
