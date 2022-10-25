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

    //@ManyToOne
    private String categoryName; // 카테고리명

    @Column(length = 5000)
    private String content; // 내용

    private String updateReason; // 수정 이유

    private LocalDateTime noticeDate; // 공지 시작일

    private LocalDateTime registerDate; // 등록일

    private LocalDateTime updateDate; // 수정일

    @Enumerated(EnumType.STRING)
    private FaqType faqType; // Faq 상태 [SHOW, CLOSE, RESERVE]

    @Column(length = 40)
    private String faqTitle; // faq 제목

    private String authInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, name = "register_user")
    private User registerUser; // 등록자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user")
    private User updateUser; // 수정자

    @Builder
    public Faq(FaqType faqType, String categoryName, String content, String updateReason, LocalDateTime noticeDate, LocalDateTime registerDate, String faqTitle, User updateUser, User registerUser, String authInfo){

        this.faqType = faqType;
        this.faqTitle = faqTitle;
        this.categoryName = categoryName;
        this.updateReason = updateReason;
        this.updateUser = updateUser;
        this.registerUser = registerUser;
        this.content = content;
        this.noticeDate = noticeDate;
        this.registerDate = registerDate;
        this.authInfo = authInfo;
    }


    public void setFaqTitle(String faqTitle) {
        this.faqTitle = faqTitle;
    }

    public void setUpdateUser(User updateUser) {this.updateUser = updateUser;}

    // Faq 수정을 위한 update method
    // 카테고리, 제목, 공지시작일, 공개상태, 수정사유, 내용

    public void updateCategoryName(String categoryName) {this.categoryName = categoryName;}

    public void updateTitle(String faqTitle) {this.faqTitle = faqTitle;}

    public void updateNoticeDate(LocalDateTime noticeDate) {this.noticeDate = noticeDate;}

    public void updateFaqType(FaqType faqType) {this.faqType = faqType;}

    public void updateUpdateReason(String updateReason) {this.updateReason = updateReason;}

    public void updateContent(String content) {this.content = content;}

    public void updateUpdateDate(LocalDateTime updateDate) {this.updateDate = updateDate;}

    public void updateUpdateUser(User updateUser) {this.updateUser = updateUser;}
}
