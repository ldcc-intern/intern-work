package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.model.dto.request.NoticeRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
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
            @RequestParam(value = "reg_start") String reg_start,
            @RequestParam(value = "reg_end") String reg_end,
            @RequestParam(value = "notice_start") String notice_start,
            @RequestParam(value = "notice_end") String notice_end,
            @RequestParam(value = "user") Long user_id,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort") String sort
    ) {
        return noticeService.getNoticeList(reg_start, reg_end, notice_start, notice_end, user_id, title, page, size, sort);
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
