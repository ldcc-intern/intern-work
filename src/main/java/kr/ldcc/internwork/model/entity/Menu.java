package kr.ldcc.internwork.model.entity;

import com.sun.xml.internal.ws.developer.Serialization;
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
@Serialization
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer orderId;
    @Enumerated(EnumType.STRING)
    private MenuType state;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu parent;
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

    public void updateMenuOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void updateMenuState(MenuType state) {
        this.state = state;
    }

    public void updateMenuTitle(String title) {
        this.title = title;
    }

    public void updateMenuParent(Menu parent) {
        this.parent = parent;
    }

    public void updateMenuUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }
}
