package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.model.dto.request.MenuRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.mapper.MenuMapper;
import kr.ldcc.internwork.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/menu")
    public Response createMenu(@RequestBody @Valid MenuRequest.CreateMenuRequest createMenuRequest) {
        return Response.ok().setData(MenuMapper.toCreateMenuResponse(menuService.createMenu(createMenuRequest)));
    }

    @GetMapping("/menu")
    public Response getMenuList() {
        return Response.ok().setData(MenuMapper.toGetMenuListResponse(menuService.getMenuList()));
    }

    @GetMapping("/menu/{menuId}")
    public Response getDetailMenu(@PathVariable("menuId") Long menuId) {
        return Response.ok().setData(MenuMapper.toGetDetailMenuResponse(menuService.getDetailMenu(menuId)));
    }

    @PutMapping("/menu/{menuId}")
    public Response updateMenu(@PathVariable("menuId") Long menuId, @RequestBody @Valid MenuRequest.UpdateMenuRequest updateMenuRequest) {
        return Response.ok().setData(MenuMapper.toUpdateMenuResponse(menuService.updateMenu(menuId, updateMenuRequest)));
    }

    @DeleteMapping("/menu/{menuId}")
    public Response deleteMenu(@PathVariable("menuId") Long menuId) {
        return Response.ok().setData(MenuMapper.toDeleteMenuResponse(menuService.deleteMenu(menuId)));
    }
}
