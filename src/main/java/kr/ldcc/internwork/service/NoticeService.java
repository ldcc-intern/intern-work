package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.model.dto.request.NoticeRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Notice;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.model.mapper.NoticeMapper;
import kr.ldcc.internwork.repository.NoticeRepository;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository, UserRepository userRepository) {
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Response createNotice(NoticeRequest.CreateNoticeRequest createNoticeRequest) {
        User user = userRepository.findById(createNoticeRequest.getUserId()).orElseThrow(() -> {
            log.error("createMenu Exception : [존재하지 않는 User ID]");
            return new InternWorkException.dataDuplicateException();
        });
        LocalDateTime startDate = LocalDateTime
                .of(LocalDate.parse(createNoticeRequest.getStartDate(), DateTimeFormatter.ofPattern("yyyyMMdd")),
                        LocalTime.of(0, 0, 0));
        Notice notice = Notice.builder()
                .title(createNoticeRequest.getTitle())
                .startDateTime(startDate)
                .content(createNoticeRequest.getContent())
                .registerUser(user)
                .build();
        try {
            noticeRepository.save(notice);
        } catch (Exception e) {
            log.error("createNotice Exception : {}", e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return NoticeMapper.toCreateNoticeResponse(notice.getId());
    }

    @Transactional
    public Response getNoticeList(Pageable pageable, String state, String regStart, String regEnd, String noticeStart, String noticeEnd, String user, String title) {
        List<Notice> notices = null;
        return NoticeMapper.toGetNoticeListResponse(notices);
    }

    @Transactional
    public Response getDetailNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Notice ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        return NoticeMapper.toGetDetailNoticeResponse(notice);
    }

    @Transactional
    public Response updateNotice(Long noticeId, NoticeRequest.UpdateNoticeRequest updateNoticeRequest) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            log.error("updateMenu Exception : [존재하지 않는 Notice ID]");
            return new InternWorkException.dataDuplicateException();
        });
        User user = userRepository.findById(updateNoticeRequest.getUserId()).orElseThrow(() -> {
            log.error("updateMenu Exception : [존재하지 않는 User ID]");
            return new InternWorkException.dataDuplicateException();
        });
        LocalDateTime startDate = null;
        if (updateNoticeRequest.getStartDate() != null) {
            startDate = LocalDateTime
                    .of(LocalDate.parse(updateNoticeRequest.getStartDate(), DateTimeFormatter.ofPattern("yyyyMMdd")),
                            LocalTime.of(0, 0, 0));
        }
        notice.updateContent((updateNoticeRequest.getContent() != null) ? updateNoticeRequest.getContent() : notice.getContent());
        notice.updateReason((updateNoticeRequest.getReason() != null) ? updateNoticeRequest.getReason() : notice.getReason());
        notice.updateStartDate((updateNoticeRequest.getStartDate() != null) ? startDate : notice.getStartDateTime());
        notice.updateState((updateNoticeRequest.getState() != null) ? updateNoticeRequest.getState() : notice.getState());
        notice.updateTitle((updateNoticeRequest.getTitle() != null) ? updateNoticeRequest.getTitle() : notice.getTitle());
        notice.updateUpdateUser(user);
        try {
            noticeRepository.save(notice);
        } catch (Exception e) {
            log.error("updateCctv Exception : {}", e.getMessage());
            throw new InternWorkException.dataUpdateException();
        }
        return NoticeMapper.toUpdateNoticeResponse(notice);
    }

    @Transactional
    public Response deleteNotice(Long noticeId) {
        Optional<Notice> notice = noticeRepository.findById(noticeId);
        if (notice.isPresent()) {
            noticeRepository.deleteById(noticeId);
            return NoticeMapper.toDeleteNoticeResponse();
        }
        throw new InternWorkException.dataNotFoundException();
    }
}
