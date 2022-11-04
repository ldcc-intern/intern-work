package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.common.types.CategoryType;
import kr.ldcc.internwork.model.entity.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
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
    private String categoryName; // 카테고리명

    private String mainCategory; // 대분류

    private Integer orderId; // 카테고리 순서

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType; // 사용여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, name = "register_user")
    private User resisterUser; // 등록자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user")
    private User updateUser; // 수정자


    @Builder
    public Category(String mainCategory, String categoryName, CategoryType categoryType, User registerUser, User updateUser, Integer orderId){
        this.mainCategory = mainCategory;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.resisterUser = registerUser;
        this.orderId = orderId;
        this.updateUser = updateUser;
    }


    /** * * * * * * * * * * * * * * * * * *
     *                                    *
     *  category 수정을 위한 update method  *
     *                                    *
     * * * * * * * * * * * * * * * * * * **/

    public void updateMainCategory(String mainCategory) {this.mainCategory = mainCategory;}
    public void updateCategoryName(String categoryName) {this.categoryName = categoryName;}

    public void updateCategoryType(CategoryType categoryType) {this.categoryType = categoryType;}

    public void updateUpdateUser(User updateUser) {this.updateUser = updateUser;}

    public void updateOrderId(Integer orderId){ this.orderId = orderId;}
}
