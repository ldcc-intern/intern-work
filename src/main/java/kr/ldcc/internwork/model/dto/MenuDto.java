package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.common.types.MenuType;
import kr.ldcc.internwork.model.entity.Menu;
import lombok.Builder;
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
    public static class GetMenuListResponse {
        private Long id;
        private Integer orderId;
        private Long parentId;
        private String title;
        private List<GetMenuListResponse> children;

        public GetMenuListResponse(Menu menu) {
            this.id = menu.getId();
            this.orderId = menu.getOrderId();
            this.parentId = menu.getParent() != null ? menu.getParent().getId() : 0;
            this.title = menu.getTitle();
        }

        @Builder
        public GetMenuListResponse(Long id, Integer orderId, Long parentId, String title, List<GetMenuListResponse> children) {
            this.id = id;
            this.orderId = orderId;
            this.parentId = parentId;
            this.title = title;
            this.children = children;
        }
    }

    @Getter
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class GetDetailMenuResponse {
        private final Long id;
        private final String main;
        private final String small;
        private final String title;
        private final MenuType state;
        private final String registerUser;
        private final String updateUser;
        private final String registerDate;
        private final String updateDate;

        public GetDetailMenuResponse(Menu menu) {
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

    public static GetDetailMenuResponse buildDetailMenu(Menu menu) {
        return new GetDetailMenuResponse(menu);
    }

    public static MenuDto.GetMenuListResponse buildGetMenuList(List<Menu> menuList) {
        Map<Long, List<GetMenuListResponse>> groupingByParent = menuList.stream().map(MenuDto.GetMenuListResponse::new).collect(Collectors.groupingBy(MenuDto.GetMenuListResponse::getParentId));
        MenuDto.GetMenuListResponse getMenuListResponse = MenuDto.GetMenuListResponse.builder().id(0L).build();
        addChildren(getMenuListResponse, groupingByParent);
        return getMenuListResponse;
    }

    private static void addChildren(GetMenuListResponse parent, Map<Long, List<GetMenuListResponse>> groupingByParentId) {
        List<GetMenuListResponse> children = groupingByParentId.get(parent.getId());
        if (children == null) {
            return;
        }
        parent.setChildren(children);
        children.forEach(menuListResponse -> addChildren(menuListResponse, groupingByParentId));
    }
}
