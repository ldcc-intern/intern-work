package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.common.types.CategoryType;
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
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName; // 카테고리명

    private String mainCategory; // 대분류

    private Long orderId; // 카테고리 순서

    @Enumerated(EnumType.STRING)
    private CategoryType useState; // 사용여부

    @ManyToOne
    @JoinColumn(updatable = false)
    private User resisterUser; // 등록자

    @ManyToOne
    @JoinColumn
    private User updateUser; // 수정자

    private LocalDateTime registerDate;

    private LocalDateTime updateDate;

    private String authInfo;

    @Builder
    public Category(String mainCategory, String categoryName, CategoryType useState, User registerUser, String authInfo){
        this.mainCategory = mainCategory;
        this.categoryName = categoryName;
        this.useState = useState;
        this.resisterUser = registerUser;
        this.authInfo = authInfo;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    /** * * * * * * * * * * * * * * * * * *
     *                                    *
     *  category 수정을 위한 update method  *
     *                                    *
     * * * * * * * * * * * * * * * * * * **/

    public void updateCategoryName(String categoryName) {this.categoryName = categoryName ;}

    public void updateCategoryType(CategoryType categoryType) {this.useState = useState ;}

    public void updateAuthInfo(String authInfo) {this.authInfo = authInfo;}
}
