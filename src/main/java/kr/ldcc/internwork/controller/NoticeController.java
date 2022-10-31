package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.common.types.validation.Enum;
import kr.ldcc.internwork.model.dto.request.NoticeRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("/notice")
    public Response createNotice(@RequestBody @Valid NoticeRequest.CreateNoticeRequest createNoticeRequest) {
        return Response.ok().setData(noticeService.createNotice(createNoticeRequest));
    }

    @GetMapping("/notice")
    public Response getNoticeList(
            @RequestParam(required = false) @Enum(enumClass = NoticeType.class) NoticeType state,
            @RequestParam(required = false) String regStart,
            @RequestParam(required = false) String regEnd,
            @RequestParam(required = false) String noticeStart,
            @RequestParam(required = false) String noticeEnd,
            @RequestParam(required = false) String user,
            @RequestParam(required = false) String title,
            Pageable pageable
    ) {
        return Response.ok().setData(noticeService.getNoticeList(regStart, regEnd, state, noticeStart, noticeEnd, user, title, pageable));
    }

    @GetMapping("/notice/{noticeId}")
    public Response getDetailNotice(@PathVariable("noticeId") Long noticeId) {
        return Response.ok().setData(noticeService.getDetailNotice(noticeId));
    }

    @PutMapping("/notice/{noticeId}")
    public Response updateNotice(@PathVariable("noticeId") Long noticeId, @RequestBody @Valid NoticeRequest.UpdateNoticeRequest updateNoticeRequest) {
        return Response.ok().setData(noticeService.updateNotice(noticeId, updateNoticeRequest));
    }

    @DeleteMapping("/notice/{noticeId}")
    public Response deleteNotice(@PathVariable("noticeId") Long noticeId) {
        return Response.ok().setData(noticeService.deleteNotice(noticeId));
    }
}
