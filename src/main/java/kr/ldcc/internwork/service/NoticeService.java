package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.dto.request.NoticeRequest;
import kr.ldcc.internwork.model.entity.Notice;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.repository.NoticeRepository;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Notice createNotice(NoticeRequest.CreateNoticeRequest createNoticeRequest) {
        User user = userRepository.findById(createNoticeRequest.getUserId()).orElseThrow(() -> {
            log.error("createMenu Exception : [존재하지 않는 User ID]");
            return new InternWorkException.dataDuplicateException();
        });
        LocalDateTime noticeDate = LocalDate.parse(createNoticeRequest.getDate(), DateTimeFormatter.ISO_DATE).atTime(LocalTime.parse(createNoticeRequest.getTime(), DateTimeFormatter.ISO_TIME));
        Notice notice = Notice.builder()
                .title(createNoticeRequest.getTitle())
                .noticeDate(noticeDate)
                .registerUser(user)
                .content(createNoticeRequest.getContent())
                .state(NoticeType.OPEN)
                .build();
        noticeRepository.save(notice);
        return notice;
    }

    @Transactional
    public Page<Notice> getNoticeList(Pageable pageable, String regStart, String regEnd, NoticeType state, String noticeStart, String noticeEnd, String userName, String title) {
        LocalDateTime registerDateStart = null;
        LocalDateTime registerDateEnd = null;
        if (regStart != null && regEnd != null) {
            registerDateStart = LocalDate.parse(regStart, DateTimeFormatter.ISO_DATE).atTime(0, 0);
            registerDateEnd = LocalDate.parse(regEnd, DateTimeFormatter.ISO_DATE).atTime(23, 59);
        }
        LocalDateTime noticeDateStart = null;
        LocalDateTime noticeDateEnd = null;
        if (noticeStart != null && noticeEnd != null) {
            noticeDateStart = LocalDate.parse(noticeStart, DateTimeFormatter.ISO_DATE).atTime(0, 0);
            noticeDateEnd = LocalDate.parse(noticeEnd, DateTimeFormatter.ISO_DATE).atTime(23, 59);
        }
        return noticeRepository.getNoticeList(pageable, registerDateStart, registerDateEnd, state, noticeDateStart, noticeDateEnd, userName, title);
    }

    @Transactional
    public Notice getDetailNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Notice ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        noticeRepository.updateView(noticeId);
        return notice;
    }

    @Transactional
    public Notice updateNotice(Long noticeId, NoticeRequest.UpdateNoticeRequest updateNoticeRequest) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Notice ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        User user = userRepository.findById(updateNoticeRequest.getUserId()).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        LocalDateTime noticeDate = updateNoticeRequest.getDate() != null && updateNoticeRequest.getTime() != null ? LocalDate.parse(updateNoticeRequest.getDate(), DateTimeFormatter.ISO_DATE).atTime(LocalTime.parse(updateNoticeRequest.getTime(), DateTimeFormatter.ISO_TIME)) : notice.getNoticeDate();
        notice.updateTitle(updateNoticeRequest.getTitle() != null ? updateNoticeRequest.getTitle() : notice.getTitle());
        notice.updateState(updateNoticeRequest.getState() != null ? updateNoticeRequest.getState() : notice.getState());
        notice.updateNoticeDate(noticeDate);
        notice.updateReason(updateNoticeRequest.getReason() != null ? updateNoticeRequest.getReason() : notice.getReason());
        notice.updateContent(updateNoticeRequest.getContent() != null ? updateNoticeRequest.getContent() : notice.getContent());
        notice.updateUpdateUser(user);
        try {
            noticeRepository.save(notice);
        } catch (Exception e) {
            log.error("updateNotice Exception : {}", e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return notice;
    }

    @Transactional
    public Notice deleteNotice(Long noticeId) {
        Optional<Notice> notice = noticeRepository.findById(noticeId);
        if (notice.isPresent()) {
            noticeRepository.deleteById(noticeId);
            return notice.get();
        }
        throw new InternWorkException.dataNotFoundException();
    }
}
