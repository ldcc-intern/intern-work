package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.common.types.FaqType;
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
@Table(name = "faq")
public class Faq extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // faq 번호

    @ManyToOne
    @JoinColumn(name = "category_name")
    private Category categoryName; // 카테고리명

    @Column(length = 5000)
    private String content; // 내용

    private String updateReason; // 수정 이유

    private LocalDateTime noticeDate; // 공지 시작일

    @Enumerated(EnumType.STRING)
    private FaqType state; // Faq 상태 [SHOW, CLOSE, RESERVE]

    @Column(length = 40)
    private String title; // faq 제목

    @ManyToOne
    @JoinColumn(updatable = false)
    private User registerUser; // 등록자

    @ManyToOne
    @JoinColumn
    private User updateUser; // 수정자

    @Builder
    public Faq(FaqType state, Category categoryName, String content, String updateReason, LocalDateTime noticeDate, String title, User updateUser, User registerUser){

        this.state = state;
        this.title = title;
        this.categoryName = categoryName;
        this.updateReason = updateReason;
        this.updateUser = updateUser;
        this.registerUser = registerUser;
        this.content = content;
        this.noticeDate = noticeDate;
    }


    // Faq 수정을 위한 update method
    // 카테고리, 제목, 공지시작일, 공개상태, 수정사유, 내용

    public void updateCategoryName(Category categoryName) {this.categoryName = categoryName;}

    public void updateTitle(String title) {this.title = title;}

    public void updateNoticeDate(LocalDateTime noticeDate) {this.noticeDate = noticeDate;}

    public void updateState(FaqType state) {this.state = state;}

    public void updateUpdateReason(String updateReason) {this.updateReason = updateReason;}

    public void updateContent(String content) {this.content = content;}

}
