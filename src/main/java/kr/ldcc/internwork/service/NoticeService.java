package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.dto.request.NoticeRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Notice;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.model.mapper.NoticeMapper;
import kr.ldcc.internwork.repository.NoticeRepository;
import kr.ldcc.internwork.repository.NoticeSpecification;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        LocalDateTime noticeDate = LocalDateTime.parse(
                createNoticeRequest.getDate() + createNoticeRequest.getTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss")
        );
        Notice notice = Notice.builder()
                .title(createNoticeRequest.getTitle())
                .noticeDate(noticeDate)
                .registerUser(user)
                .content(createNoticeRequest.getContent())
                .state(NoticeType.OPEN)
                .build();
        noticeRepository.save(notice);
        return NoticeMapper.toCreateNoticeResponse(notice.getId());
    }

    @Transactional
    public Response getNoticeList(Pageable pageable, String regStart, String regEnd, NoticeType state, String noticeStart, String noticeEnd, String userName, String title) {
        Specification<Notice> specification = (root, query, criteriaBuilder) -> null;
        if (regStart != null && regEnd != null) {
            LocalDateTime registerDateStart = LocalDateTime.parse(regStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime registerDateEnd = LocalDateTime.parse(regEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            specification = specification.and(NoticeSpecification.betweenRegisterDate(registerDateStart, registerDateEnd));
        }
        if (state != null) {
            specification = specification.and(NoticeSpecification.equalState(state));
        }
        if (noticeStart != null && noticeEnd != null) {
            LocalDateTime noticeDateStart = LocalDateTime.parse(noticeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime noticeDateEnd = LocalDateTime.parse(noticeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            specification = specification.and(NoticeSpecification.betweenNoticeDate(noticeDateStart, noticeDateEnd));
        }
        if (userName != null) {
            specification = specification.and(NoticeSpecification.equalRegisterUser(userName));
        }
        if (title != null) {
            specification = specification.and(NoticeSpecification.equalTitle(title));
        }
        Page<Notice> notices = noticeRepository.findAll(specification, pageable);
        return NoticeMapper.toGetNoticeListResponse(notices);
    }

    @Transactional
    public Response getDetailNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Notice ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        updateView(noticeId);
        return NoticeMapper.toGetDetailNoticeResponse(notice);
    }

    @Transactional
    public Response updateNotice(Long noticeId, NoticeRequest.UpdateNoticeRequest updateNoticeRequest) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 Notice ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        User user = userRepository.findById(updateNoticeRequest.getUserId()).orElseThrow(() -> {
            log.error("getDetailMenu Exception : [존재하지 않는 User ID]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new InternWorkException.dataNotFoundException();
        });
        LocalDateTime noticeDate = updateNoticeRequest.getDate() != null && updateNoticeRequest.getTime() != null ? LocalDateTime.parse(
                updateNoticeRequest.getDate() + updateNoticeRequest.getTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss")
        ) : notice.getNoticeDate();
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

    public int updateView(Long id) {
        return noticeRepository.updateView(id);
    }
}
