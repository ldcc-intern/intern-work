package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.common.types.FaqType;
import kr.ldcc.internwork.model.entity.base.BaseEntity;
import lombok.AccessLevel;
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
    private Long faqId;
    @Column(length = 5000)
    private String content;
    private String reason;
    private LocalDateTime noticeDate; // 공지 시작일
    @Enumerated(EnumType.STRING)
    private FaqType state; // Faq 상태 [SHOW, CLOSE, RESERVE]
    @Column(length = 40)
    private String title;
    @ManyToOne
    @JoinColumn
    private Category category;
    @ManyToOne
    @JoinColumn(updatable = false)
    private User registerUser; // 등록자
    @ManyToOne
    @JoinColumn
    private User updateUser; //수정자
}
