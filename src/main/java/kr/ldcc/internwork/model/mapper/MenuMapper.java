package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.MenuDto;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.Menu;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuMapper {
    public static MenuDto.CreateMenuResponse toCreateMenuResponse(Long id) {
        return new MenuDto.CreateMenuResponse().setId(id);
    }

    public static Response toGetMenuListResponse(List<Menu> menus) {
        Map<Long, List<MenuDto.MenuListResponse>> groupingByParent = menus.stream().map(menu -> MenuDto.MenuListResponse.builder()
                .id(menu.getId())
                .orderId(menu.getOrderId())
                .parentId(menu.getParent() != null ? menu.getParent().getId() : 0)
                .title(menu.getTitle())
                .build()
        ).collect(Collectors.groupingBy(menuListResponse -> menuListResponse.getParentId()));
        MenuDto.MenuListResponse menuListResponse = MenuDto.MenuListResponse.builder().id(0L).build();
        addChildren(menuListResponse, groupingByParent);
        return Response.ok().setData(menuListResponse);
    }

    public static Response toGetDetailMenuResponse(Menu menu) {
        return Response.ok().setData(new MenuDto.GetDetailMenuResponse()
                .setId(menu.getId())
                .setMain(menu.getParent() != null ? (menu.getParent().getParent() != null ? menu.getParent().getParent().getTitle() : menu.getParent().getTitle()) : null)
                .setSmall(menu.getParent() != null ? (menu.getParent().getParent() != null ? menu.getParent().getTitle() : null) : null)
                .setTitle(menu.getTitle())
                .setState(menu.getState())
                .setRegisterUser(menu.getRegisterUser().getName())
                .setUpdateUser((menu.getUpdateUser() != null) ? menu.getUpdateUser().getName() : null)
                .setRegisterDate(menu.getRegisterDate())
                .setUpdateDate(menu.getUpdateDate())
        );
    }

    public static Response toUpdateMenuResponse(Menu menu) {
        return Response.ok().setData(new MenuDto.UpdateMenuResponse()
                .setId(menu.getId())
                .setOrderId(menu.getOrderId())
                .setParent(menu.getParent().getId())
                .setState(menu.getState())
                .setTitle(menu.getTitle())
                .setRegisterUser(menu.getRegisterUser())
                .setUpdateUser(menu.getUpdateUser())
        );
    }

    public static Response toDeleteMenuResponse() {
        return Response.ok();
    }

    private static void addChildren(MenuDto.MenuListResponse parent, Map<Long, List<MenuDto.MenuListResponse>> groupingByParentId) {
        List<MenuDto.MenuListResponse> children = groupingByParentId.get(parent.getId());
        if (children == null) {
            return;
        }
        parent.setChildren(children);
        children.forEach(menuListResponse -> addChildren(menuListResponse, groupingByParentId));
    }
}
