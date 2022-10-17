package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.common.types.CategoryType;
import kr.ldcc.internwork.model.entity.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String mainCategory; //대분류
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private CategoryType useState; // 사용여부
    private String name; // 카테고리명
    @ManyToOne
    @JoinColumn(updatable = false)
    private User resisterUser;
    @ManyToOne
    @JoinColumn
    private User updateUser;
}
