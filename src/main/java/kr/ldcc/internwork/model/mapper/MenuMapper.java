package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.MenuDto;
import kr.ldcc.internwork.model.entity.Menu;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuMapper {
    public static MenuDto.CreateMenuResponse toCreateMenuResponse(Menu menu) {
        return new MenuDto.CreateMenuResponse().setId(menu.getId());
    }

    public static MenuDto.GetMenuListResponse toGetMenuListResponse(List<Menu> menus) {
        Map<Long, List<MenuDto.GetMenuListResponse>> groupingByParent = menus.stream().map(menu -> MenuDto.GetMenuListResponse.builder()
                .id(menu.getId())
                .orderId(menu.getOrderId())
                .parentId(menu.getParent() != null ? menu.getParent().getId() : 0)
                .title(menu.getTitle())
                .build()
        ).collect(Collectors.groupingBy(menuListResponse -> menuListResponse.getParentId()));
        MenuDto.GetMenuListResponse getMenuListResponse = MenuDto.GetMenuListResponse.builder().id(0L).build();
        addChildren(getMenuListResponse, groupingByParent);
        return getMenuListResponse;
    }

    public static MenuDto.GetDetailMenuResponse toGetDetailMenuResponse(Menu menu) {
        return new MenuDto.GetDetailMenuResponse()
                .setId(menu.getId())
                .setMain(menu.getParent() != null ? (menu.getParent().getParent() != null ? menu.getParent().getParent().getTitle() : menu.getParent().getTitle()) : null)
                .setSmall(menu.getParent() != null ? (menu.getParent().getParent() != null ? menu.getParent().getTitle() : null) : null)
                .setTitle(menu.getTitle())
                .setState(menu.getState())
                .setRegisterUser(menu.getRegisterUser().getName())
                .setUpdateUser((menu.getUpdateUser() != null) ? menu.getUpdateUser().getName() : null)
                .setRegisterDate(menu.getRegisterDate())
                .setUpdateDate(menu.getUpdateDate() != menu.getRegisterDate() ? menu.getUpdateDate() : null);
    }

    public static MenuDto.UpdateMenuResponse toUpdateMenuResponse(Menu menu) {
        return new MenuDto.UpdateMenuResponse()
                .setId(menu.getId())
                .setOrderId(menu.getOrderId())
                .setParent(menu.getParent().getId())
                .setState(menu.getState())
                .setTitle(menu.getTitle())
                .setRegisterUser(menu.getRegisterUser().getName())
                .setUpdateUser(menu.getUpdateUser().getName());
    }

    public static MenuDto.DeleteMenuResponse toDeleteMenuResponse(Optional<Menu> menu) {
        return new MenuDto.DeleteMenuResponse().setId(menu.get().getId());
    }

    private static void addChildren(MenuDto.GetMenuListResponse parent, Map<Long, List<MenuDto.GetMenuListResponse>> groupingByParentId) {
        List<MenuDto.GetMenuListResponse> children = groupingByParentId.get(parent.getId());
        if (children == null) {
            return;
        }
        parent.setChildren(children);
        children.forEach(menuListResponse -> addChildren(menuListResponse, groupingByParentId));
    }
}
