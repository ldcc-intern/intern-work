package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.model.dto.MenuDto;
import kr.ldcc.internwork.model.dto.request.MenuRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/menu")
    public Response<Long> createMenu(@RequestBody @Valid MenuRequest request) {
        return Response.<Long>ok().setData(menuService.createMenu(request));
    }

    @GetMapping("/menu")
    public Response<MenuDto.GetMenuListResponse> getMenuList() {
        return Response.<MenuDto.GetMenuListResponse>ok().setData(menuService.getMenuList());
    }

    @GetMapping("/menu/{menuId}")
    public Response<MenuDto.GetDetailMenuResponse> getDetailMenu(@PathVariable("menuId") Long menuId) {
        return Response.<MenuDto.GetDetailMenuResponse>ok().setData(menuService.getDetailMenu(menuId));
    }

    @PutMapping("/menu/{menuId}")
    public Response<MenuDto.UpdateMenuResponse> updateMenu(@PathVariable("menuId") Long menuId, @RequestBody @Valid MenuRequest request) {
        return Response.<MenuDto.UpdateMenuResponse>ok().setData(menuService.updateMenu(menuId, request));
    }

    @DeleteMapping("/menu/{menuId}")
    public Response<Object> deleteMenu(@PathVariable("menuId") Long menuId) {
        menuService.deleteMenu(menuId);
        return Response.ok();
    }
}
