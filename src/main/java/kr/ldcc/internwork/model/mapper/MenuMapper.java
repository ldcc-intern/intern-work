package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.MenuDto;
import kr.ldcc.internwork.model.entity.Menu;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuMapper {
    public static Object toMenuListResponse(List<Menu> menus) {
        List<MenuDto.MenuListResponse> menuListResponses = new ArrayList<>();
        menus.stream().forEach(menu -> new MenuDto.MenuListResponse());
        return menuListResponses;
    }
}
