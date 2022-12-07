package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException.DataDeleteException;
import kr.ldcc.internwork.common.exception.InternWorkException.DataDuplicateException;
import kr.ldcc.internwork.common.exception.InternWorkException.DataNotFoundException;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.dto.NoticeDto;
import kr.ldcc.internwork.model.dto.NoticeDto.GetNoticeList;
import kr.ldcc.internwork.model.dto.request.NoticeRequest;
import kr.ldcc.internwork.model.entity.Notice;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.repository.NoticeRepository;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Transactional
@Log4j2
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public void createNotice(NoticeRequest.CreateNoticeRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> {
            log.error("createNotice Exception | [존재하지 않는 User ID : " + request.getUserId() + "]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        LocalDateTime noticeDate = LocalDate.parse(request.getDate(), DateTimeFormatter.ISO_DATE)
                .atTime(LocalTime.parse(request.getTime(), DateTimeFormatter.ISO_TIME));
        Notice notice = Notice.builder()
                .title(request.getTitle())
                .noticeDate(noticeDate)
                .registerUser(user)
                .content(request.getContent())
                .state(NoticeType.OPEN)
                .build();
        noticeRepository.save(notice);
    }

    public Page<GetNoticeList> getNoticeList(String registerStart, String registerEnd, NoticeType state, String noticeDateStart, String noticeDateEnd, String userName, String title, Pageable pageable) {
        LocalDate registerStartDate = null;
        LocalDate registerEndDate = null;
        LocalDate noticeStartDate = null;
        LocalDate noticeEndDate = null;
        if (registerStart != null && registerEnd != null) {
            registerStartDate = LocalDate.parse(registerStart, DateTimeFormatter.ISO_DATE);
            registerEndDate = LocalDate.parse(registerEnd, DateTimeFormatter.ISO_DATE);
        }
        if (noticeDateStart != null && noticeDateEnd != null) {
            noticeStartDate = LocalDate.parse(noticeDateStart, DateTimeFormatter.ISO_DATE);
            noticeEndDate = LocalDate.parse(noticeDateEnd, DateTimeFormatter.ISO_DATE);
        }
        return NoticeDto.buildNoticePage(noticeRepository.getNoticeList(registerStartDate, registerEndDate, state, noticeStartDate, noticeEndDate, userName, title, pageable));
    }

    public NoticeDto getDetailNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            log.error("getDetailNotice Exception | [존재하지 않는 Notice ID : " + noticeId + "]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        notice.updateView(notice.getView() != null ? notice.getView() : 1);
        return NoticeDto.buildDetailNotice(notice);
    }

    public void updateNotice(Long noticeId, NoticeRequest.UpdateNoticeRequest request) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            log.error("updateNotice Exception | [존재하지 않는 Notice ID : " + noticeId + "]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> {
            log.error("updateNotice Exception | [존재하지 않는 User ID : " + request.getUserId() + "]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        LocalDateTime noticeDate = request.getDate() != null && request.getTime() != null ? LocalDate.parse(request.getDate(), DateTimeFormatter.ISO_DATE).atTime(LocalTime.parse(request.getTime(), DateTimeFormatter.ISO_TIME)) : notice.getNoticeDate();
        notice.updateTitle(request.getTitle() != null ? request.getTitle() : notice.getTitle());
        notice.updateState(request.getState() != null ? request.getState() : notice.getState());
        notice.updateNoticeDate(noticeDate);
        notice.updateReason(request.getReason() != null ? request.getReason() : notice.getReason());
        notice.updateContent(request.getContent() != null ? request.getContent() : notice.getContent());
        notice.updateUpdateUser(user);
        try {
            noticeRepository.save(notice);
        } catch (Exception e) {
            log.error("updateNotice Exception", ExceptionCode.DATA_DUPLICATE_EXCEPTION, e.getMessage());
            throw new DataDuplicateException(ExceptionCode.DATA_DUPLICATE_EXCEPTION);
        }
    }

    public void deleteNotice(Long noticeId) {
        Optional<Notice> notice = noticeRepository.findById(noticeId);
        if (notice.isPresent()) {
            noticeRepository.deleteById(noticeId);
            return;
        }
        log.error("deleteNotice Exception | [존재하지 않는 Notice ID : " + noticeId + "]", ExceptionCode.DATA_DELETE_EXCEPTION);
        throw new DataDeleteException(ExceptionCode.DATA_DELETE_EXCEPTION);
    }
}
