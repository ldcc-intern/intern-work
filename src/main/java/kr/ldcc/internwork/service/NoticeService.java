package kr.ldcc.internwork.service;

import kr.ldcc.internwork.model.dto.request.NoticeRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.mapper.NoticeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class NoticeService {
    @Transactional
    public Response createNotice(NoticeRequest.CreateNoticeRequest createNoticeRequest) {
        return NoticeMapper.toCreateNoticeResponse();
    }

    @Transactional
    public Response getNoticeList(String reg_start, String reg_end, String notice_start, String notice_end, Long user_id, String title, int page, int size, String sort) {
        return NoticeMapper.toGetNoticeListResponse();
    }

    @Transactional
    public Response getDetailNotice(Long noticeId) {
        return NoticeMapper.toGetDetailNoticeResponse();
    }

    @Transactional
    public Response updateNotice(Long noticeId, NoticeRequest.UpdateNoticeRequest updateNoticeRequest) {
        return NoticeMapper.toUpdateNoticeResponse();
    }

    @Transactional
    public Response deleteNotice(Long noticeId) {
        return NoticeMapper.toDeleteNoticeResponse();
    }
}
