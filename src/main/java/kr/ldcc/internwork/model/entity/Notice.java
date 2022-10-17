package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.common.types.NoticeType;
import kr.ldcc.internwork.model.entity.base.BaseEntity;
import lombok.AccessLevel;
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
    private String reason;
    @Column(nullable = false)
    private LocalDateTime startDate;
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
}
