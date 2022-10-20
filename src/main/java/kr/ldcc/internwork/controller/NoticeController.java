package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.model.dto.request.NoticeRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/notice")
    public Response createNotice(@RequestBody @Valid NoticeRequest.CreateNoticeRequest createNoticeRequest) {
        return noticeService.createNotice(createNoticeRequest);
    }

    @GetMapping("/notice")
    public Response getNoticeList(
            Pageable pageable,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String regStart,
            @RequestParam(required = false) String regEnd,
            @RequestParam(required = false) String noticeStart,
            @RequestParam(required = false) String noticeEnd,
            @RequestParam(required = false) String user,
            @RequestParam(required = false) String title
    ) {
        return noticeService.getNoticeList(pageable, state, regStart, regEnd, noticeStart, noticeEnd, user, title);
    }

    @GetMapping("/notice/{notice_id}")
    public Response getDetailNotice(@PathVariable Long noticeId) {
        return noticeService.getDetailNotice(noticeId);
    }

    @PutMapping("/notice/{noticeId}")
    public Response updateNotice(@PathVariable Long noticeId, @RequestBody @Valid NoticeRequest.UpdateNoticeRequest updateNoticeRequest) {
        return noticeService.updateNotice(noticeId, updateNoticeRequest);
    }

    @DeleteMapping("/notice/{noticeId}")
    public Response deleteNotice(@PathVariable Long noticeId) {
        return noticeService.deleteNotice(noticeId);
    }
}
