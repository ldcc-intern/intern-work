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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_name")
    private Category categoryName; // 카테고리명

    private String mainCategory; // 대분류

    private Long orderId; // 카테고리 순서

    @Enumerated(EnumType.STRING)
    private CategoryType useState; // 사용여부

    @ManyToOne
    @JoinColumn(updatable = false)
    private User resisterUser;

    @ManyToOne
    @JoinColumn
    private User updateUser;

    public void setCategoryName(Category categoryName) {
        this.categoryName = categoryName;
    }
}
