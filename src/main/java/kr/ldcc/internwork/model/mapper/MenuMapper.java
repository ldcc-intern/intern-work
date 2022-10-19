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
    public static Response toCreateMenuResponse(Long id) {
        return Response.ok().setData(new MenuDto.CreateMenuResponse().setId(id));
    }

    public static Response toGetMenuListResponse(List<Menu> menus) {
        Map<Long, List<MenuDto.MenuListResponse>> groupingByParent = menus.stream().map(menu -> MenuDto.MenuListResponse.builder()
                .id(menu.getId())
                .orderId(menu.getOrderId())
                .parentId((menu.getParent() != null) ? menu.getParent().getId() : 0)
                .title(menu.getTitle())
                .build()
        ).collect(Collectors.groupingBy(menuListResponse -> menuListResponse.getParentId()));
        MenuDto.MenuListResponse ROOT = MenuDto.MenuListResponse.builder()
                .id(0L)
                .orderId(0)
                .parentId(0L)
                .title("ROOT")
                .build();
        addChildren(ROOT, groupingByParent);
        return Response.ok().setData(ROOT);
    }

    public static Response toGetDetailMenuResponse(Menu menu) {
        return Response.ok().setData(new MenuDto.MenuDetailResponse()
                .setId(menu.getId())
                .setOrderId(menu.getOrderId())
                .setUpdateDate(menu.getUpdateDate())
                .setRegisterDate(menu.getRegisterDate())
                .setParentId((menu.getParent() != null) ? menu.getParent().getId() : null)
                .setState(menu.getState())
                .setTitle(menu.getTitle())
                .setRegisterUser(menu.getRegisterUser().getName())
                .setUpdateUser((menu.getUpdateUser() != null) ? menu.getUpdateUser().getName() : null)
                .setMain((menu.getParent().getParent() != null) ? menu.getParent().getParent().getTitle() : menu.getParent().getTitle())
                .setSmall((menu.getParent().getParent() != null) ? menu.getParent().getTitle() : null)
        );
    }

    public static Response toUpdateMenuResponse(Menu menu) {
        return Response.ok().setData(ObjectMapperUtils.map(menu, MenuDto.class));
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
