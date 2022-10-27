package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.dto.request.NoticeRequest;
import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.entity.Notice;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.repository.NoticeRepository;
import kr.ldcc.internwork.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NoticeServiceTest {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserService userService;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void createNotice() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("Test 이름"));
        NoticeRequest.CreateNoticeRequest createNoticeRequest = new NoticeRequest.CreateNoticeRequest()
                .setTitle("Test 제목")
                .setDate("2022-10-27")
                .setTime("12:47:50")
                .setContent("content")
                .setUserId(registerUser.getId());
//        when
        Notice notice = noticeService.createNotice(createNoticeRequest);
//        then
        Notice findNotice = noticeRepository.findById(notice.getId()).get();
        LocalDateTime localDateTime = LocalDateTime.parse(
                createNoticeRequest.getDate() + createNoticeRequest.getTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss")
        );
        assertEquals(findNotice.getTitle(), notice.getTitle());
        assertEquals(findNotice.getNoticeDate(), notice.getNoticeDate());
        assertEquals(findNotice.getContent(), notice.getContent());
        assertEquals(findNotice.getRegisterUser().getId(), notice.getRegisterUser().getId());
        assertEquals(findNotice.getTitle(), createNoticeRequest.getTitle());
        assertEquals(findNotice.getNoticeDate(), localDateTime);
        assertEquals(findNotice.getContent(), createNoticeRequest.getContent());
        assertEquals(findNotice.getRegisterUser().getId(), createNoticeRequest.getUserId());
    }

    @Test
    void updateNotice() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("Test 이름"));
        NoticeRequest.CreateNoticeRequest createNoticeRequest = new NoticeRequest.CreateNoticeRequest()
                .setTitle("Test 제목")
                .setDate("2022-10-27")
                .setTime("12:47:50")
                .setContent("content")
                .setUserId(registerUser.getId());
        Notice newNotice = noticeService.createNotice(createNoticeRequest);
        User updateUser = userService.createUser(new UserRequest.CreateUserRequest().setName("Test 이름"));
        NoticeRequest.UpdateNoticeRequest updateNoticeRequest = new NoticeRequest.UpdateNoticeRequest()
                .setTitle("Test Update 제목")
                .setState(NoticeType.CLOSE)
                .setDate("2022-10-27")
                .setTime("13:12:50")
                .setReason("그냥")
                .setContent("변경 content")
                .setUserId(updateUser.getId());
//        when
        Notice notice = noticeService.updateNotice(newNotice.getId(), updateNoticeRequest);
//        then
        Notice findNotice = noticeRepository.findById(notice.getId()).get();
        LocalDateTime localDateTime = LocalDateTime.parse(
                updateNoticeRequest.getDate() + updateNoticeRequest.getTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss")
        );
        assertEquals(findNotice.getTitle(), notice.getTitle());
        assertEquals(findNotice.getState(), notice.getState());
        assertEquals(findNotice.getNoticeDate(), notice.getNoticeDate());
        assertEquals(findNotice.getReason(), notice.getReason());
        assertEquals(findNotice.getContent(), notice.getContent());
        assertEquals(findNotice.getRegisterUser().getId(), notice.getRegisterUser().getId());
        assertEquals(findNotice.getUpdateUser().getId(), notice.getUpdateUser().getId());
        assertEquals(findNotice.getTitle(), updateNoticeRequest.getTitle());
        assertEquals(findNotice.getState(), updateNoticeRequest.getState());
        assertEquals(findNotice.getNoticeDate(), localDateTime);
        assertEquals(findNotice.getReason(), updateNoticeRequest.getReason());
        assertEquals(findNotice.getContent(), updateNoticeRequest.getContent());
        assertEquals(findNotice.getUpdateUser().getId(), updateNoticeRequest.getUserId());
    }

    @Test
    void deleteNotice() {
//        given
        User registerUser = userService.createUser(new UserRequest.CreateUserRequest().setName("Test 이름"));
        NoticeRequest.CreateNoticeRequest createNoticeRequest = new NoticeRequest.CreateNoticeRequest()
                .setTitle("Test 제목")
                .setDate("2022-10-27")
                .setTime("12:47:50")
                .setContent("content")
                .setUserId(registerUser.getId());
        Notice newNotice = noticeService.createNotice(createNoticeRequest);
//        when
        Notice notice = noticeService.deleteNotice(newNotice.getId());
//        then
        boolean findMenu = noticeRepository.findById(notice.getId()).isPresent();
        assertEquals(notice.getId(), newNotice.getId());
        assertEquals(findMenu, false);
    }
}