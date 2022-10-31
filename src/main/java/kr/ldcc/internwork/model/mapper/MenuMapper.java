package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.MenuDto;
import kr.ldcc.internwork.model.dto.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuMapper {
    public static Response toCreateMenuResponse(MenuDto.CreateMenuResponse createMenuResponse) {
        return Response.ok().setData(createMenuResponse);
    }

    public static Response toGetMenuListResponse(MenuDto.GetMenuListResponse getMenuListResponse) {
        return Response.ok().setData(getMenuListResponse);
    }

    public static Response toGetDetailMenuResponse(MenuDto.GetDetailMenuResponse getDetailMenuResponse) {
        return Response.ok().setData(getDetailMenuResponse);
    }

    public static Response toUpdateMenuResponse(MenuDto.UpdateMenuResponse updateMenuResponse) {
        return Response.ok().setData(updateMenuResponse);
    }

    public static Response toDeleteMenuResponse(MenuDto.DeleteMenuResponse deleteMenuResponse) {
        return Response.ok().setData(deleteMenuResponse);
    }
}
