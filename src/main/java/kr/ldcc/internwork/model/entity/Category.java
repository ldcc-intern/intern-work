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

    private Integer orderId; // 카테고리 순서

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType; // 사용여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private User resisterUser; // 등록자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User updateUser; // 수정자

    private LocalDateTime registerDate;

    private LocalDateTime updateDate;

    private String authInfo;

    @Builder
    public Category(String mainCategory, String categoryName, CategoryType categoryType, LocalDateTime registerDate, User registerUser, User updateUser, String authInfo, Integer orderId){
        this.mainCategory = mainCategory;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.resisterUser = registerUser;
        this.registerDate = registerDate;
        this.authInfo = authInfo;
        this.orderId = orderId;
        this.updateUser = updateUser;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setUpdateUser(User updateUser) {this.updateUser = updateUser;}

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public void setResisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }


    /** * * * * * * * * * * * * * * * * * *
     *                                    *
     *  category 수정을 위한 update method  *
     *                                    *
     * * * * * * * * * * * * * * * * * * **/

    public void updateCategoryName(String categoryName) {this.categoryName = categoryName ;}

    public void updateCategoryType(CategoryType categoryType) {this.categoryType = categoryType ;}

    public void updateAuthInfo(String authInfo) {this.authInfo = authInfo;}

    public void updateUpdatedate(LocalDateTime updateDate) {this.updateDate = updateDate;}

    public void updateUpdateUser(User updateUser) {this.updateUser = updateUser;}
}
