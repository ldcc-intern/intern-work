package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.dto.request.NoticeRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/notice")
@Slf4j
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping
    public Response<Long> createNotice(@RequestBody @Valid NoticeRequest.CreateNoticeRequest createNoticeRequest) {
        log.info("[createNotice]");
        noticeService.createNotice(createNoticeRequest);
        return Response.ok();
    }

    @GetMapping
    public Response getNoticeList(
            @RequestParam(required = false) NoticeType state,
            @RequestParam(required = false) String regStart,
            @RequestParam(required = false) String regEnd,
            @RequestParam(required = false) String noticeStart,
            @RequestParam(required = false) String noticeEnd,
            @RequestParam(required = false) String user,
            @RequestParam(required = false) String title,
            @PageableDefault(size = 25) Pageable pageable
    ) {
        log.info("[getNoticeList]");
        return Response.ok().setData(noticeService.getNoticeList(regStart, regEnd, state, noticeStart, noticeEnd, user, title, pageable));
    }

    @GetMapping("/{noticeId}")
    public Response getDetailNotice(@PathVariable("noticeId") Long noticeId) {
        log.info("[getDetailNotice]");
        return Response.ok().setData(noticeService.getDetailNotice(noticeId));
    }

    @PutMapping("/{noticeId}")
    public Response updateNotice(@PathVariable("noticeId") Long noticeId, @RequestBody @Valid NoticeRequest.UpdateNoticeRequest updateNoticeRequest) {
        log.info("[updateNotice]");
        noticeService.updateNotice(noticeId, updateNoticeRequest);
        return Response.ok();
    }

    @DeleteMapping("/{noticeId}")
    public Response deleteNotice(@PathVariable("noticeId") Long noticeId) {
        log.info("[deleteNotice]");
        noticeService.deleteNotice(noticeId);
        return Response.ok();
    }
}
