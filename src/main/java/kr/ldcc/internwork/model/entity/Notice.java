package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.entity.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 5000)
    private String content;
    @Column(nullable = false)
    private LocalDateTime noticeDate;
    private String reason;
    @Enumerated(EnumType.STRING)
    private NoticeType state;
    @Column(length = 40, nullable = false)
    private String title;
    private Integer view;
    @ManyToOne
    @JoinColumn(name = "register_user", updatable = false)
    private User registerUser;
    @ManyToOne
    @JoinColumn(name = "update_user")
    private User updateUser;

    @Builder
    public Notice(String content, String reason, LocalDateTime noticeDate, NoticeType state, String title, Integer view, User registerUser, User updateUser) {
        this.content = content;
        this.reason = reason;
        this.noticeDate = noticeDate;
        this.state = state;
        this.title = title;
        this.view = view;
        this.registerUser = registerUser;
        this.updateUser = updateUser;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateReason(String reason) {
        this.reason = reason;
    }

    public void updateStartDate(LocalDateTime startDate) {
        this.noticeDate = startDate;
    }

    public void updateState(NoticeType state) {
        this.state = state;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }
}
