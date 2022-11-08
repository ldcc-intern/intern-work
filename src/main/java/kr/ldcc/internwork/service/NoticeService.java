package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.dto.NoticeDto;
import kr.ldcc.internwork.model.dto.request.NoticeRequest;
import kr.ldcc.internwork.model.entity.Notice;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.model.mapper.NoticeMapper;
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
    public Long createNotice(NoticeRequest.CreateNoticeRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> {
            throw new InternWorkException.dataNotFoundException(
                    "createNotice Exception : [존재하지 않는 User ID] : "
                            + ExceptionCode.DATA_NOT_FOUND_EXCEPTION.getMessage());
        });
        LocalDateTime noticeDate = LocalDate.parse(
                        request.getDate(), DateTimeFormatter.ISO_DATE
                )
                .atTime(
                        LocalTime.parse(request.getTime(), DateTimeFormatter.ISO_TIME)
                );
        Notice notice = Notice.builder()
                .title(request.getTitle())
                .noticeDate(noticeDate)
                .registerUser(user)
                .content(request.getContent())
                .state(NoticeType.OPEN)
                .build();
        noticeRepository.save(notice);
        return notice.getId();
    }

    @Transactional
    public Page<NoticeDto.GetNoticeListResponse> getNoticeList(String registerStart, String registerEnd, NoticeType state, String noticeDateStart, String noticeDateEnd, String userName, String title, Pageable pageable) {
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
        return NoticeMapper.toGetNoticeListResponse(noticeRepository.getNoticeList(registerStartDate, registerEndDate, state, noticeStartDate, noticeEndDate, userName, title, pageable));
    }

    @Transactional
    public NoticeDto.GetDetailNoticeResponse getDetailNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            throw new InternWorkException.dataNotFoundException(
                    "getDetailNotice Exception : [존재하지 않는 Notice ID] : "
                            + ExceptionCode.DATA_NOT_FOUND_EXCEPTION.getMessage());
        });
        notice.updateView(notice.getView() != null ? notice.getView() : 1);
        return NoticeMapper.toGetDetailNoticeResponse(notice);
    }

    @Transactional
    public NoticeDto.UpdateNoticeResponse updateNotice(Long noticeId, NoticeRequest.UpdateNoticeRequest request) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            throw new InternWorkException.dataNotFoundException(
                    "updateNotice Exception : [존재하지 않는 Notice ID] : "
                            + ExceptionCode.DATA_NOT_FOUND_EXCEPTION.getMessage());
        });
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> {
            throw new InternWorkException.dataNotFoundException(
                    "updateNotice Exception : [존재하지 않는 User ID] : "
                            + ExceptionCode.DATA_NOT_FOUND_EXCEPTION.getMessage());
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
            throw new InternWorkException.dataDuplicateException(
                    "updateNotice Exception : "
                            + ExceptionCode.DATA_DUPLICATE_EXCEPTION.getMessage() + " : "
                            + e.getMessage());
        }
        return NoticeMapper.toUpdateNoticeResponse(notice);
    }

    @Transactional
    public void deleteNotice(Long noticeId) {
        Optional<Notice> notice = noticeRepository.findById(noticeId);
        if (notice.isPresent()) {
            noticeRepository.deleteById(noticeId);
            return;
        }
        throw new InternWorkException.dataDeleteException(
                "deleteNotice Exception : [존재하지 않는 Notice ID] : "
                        + ExceptionCode.DATA_NOT_FOUND_EXCEPTION.getMessage());
    }
}
