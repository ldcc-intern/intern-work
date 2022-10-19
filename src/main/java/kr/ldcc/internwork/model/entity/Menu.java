package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.common.types.MenuType;
import kr.ldcc.internwork.model.entity.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu parent;
    @Enumerated(EnumType.STRING)
    private MenuType state;
    private String title;
    @ManyToOne
    @JoinColumn(name = "register_user", updatable = false)
    private User registerUser;
    @ManyToOne
    @JoinColumn(name = "update_user")
    private User updateUser;

    @Builder
    public Menu(Integer orderId, Menu parent, MenuType state, String title, User registerUser, User updateUser) {
        this.orderId = orderId;
        this.parent = parent;
        this.state = state;
        this.title = title;
        this.registerUser = registerUser;
        this.updateUser = updateUser;
    }

    public void updateMenuOrderId(int orderId) {
        this.orderId = orderId;
    }
}
