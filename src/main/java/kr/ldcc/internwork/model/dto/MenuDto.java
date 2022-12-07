package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.MenuType;
import kr.ldcc.internwork.model.entity.Menu;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuDto {
    @Getter
    @Setter
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class MenuList {
        private Long id;
        private Integer orderId;
        private Long parentId;
        private String title;
        private List<MenuList> children;

        public MenuList(Menu menu) {
            this.id = menu.getId();
            this.orderId = menu.getOrderId();
            this.parentId = menu.getParent() != null ? menu.getParent().getId() : 0;
            this.title = menu.getTitle();
        }

        public MenuList(Long id) {
            this.id = id;
        }
    }

    @Getter
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class DetailMenu {
        private final Long id;
        private final String main;
        private final String small;
        private final String title;
        private final MenuType state;
        private final String registerUser;
        private final String updateUser;
        private final String registerDate;
        private final String updateDate;

        public DetailMenu(Menu menu) {
            this.id = menu.getId();
            this.main = menu.getParent() != null ? (menu.getParent().getParent() != null ? menu.getParent().getParent().getTitle() : menu.getParent().getTitle()) : null;
            this.small = menu.getParent() != null ? (menu.getParent().getParent() != null ? menu.getParent().getTitle() : null) : null;
            this.title = menu.getTitle();
            this.state = menu.getState();
            this.registerUser = menu.getRegisterUser().getName();
            this.updateUser = (menu.getUpdateUser() != null) ? menu.getUpdateUser().getName() : null;
            this.registerDate = menu.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
            this.updateDate = menu.getUpdateDate() != menu.getRegisterDate() ? menu.getUpdateDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) : null;
        }
    }

    public static DetailMenu buildDetailMenu(Menu menu) {
        return new DetailMenu(menu);
    }

    public static MenuList buildGetMenuList(List<Menu> menuList) {
        Map<Long, List<MenuList>> groupingByParent = menuList.stream().map(MenuList::new).collect(Collectors.groupingBy(MenuList::getParentId));
        MenuList result = new MenuList(0L);
        addChildren(result, groupingByParent);
        return result;
    }

    private static void addChildren(MenuList parent, Map<Long, List<MenuList>> groupingByParentId) {
        List<MenuList> children = groupingByParentId.get(parent.getId());
        if (children == null) {
            return;
        }
        parent.setChildren(children);
        children.forEach(menuList -> addChildren(menuList, groupingByParentId));
    }
}
