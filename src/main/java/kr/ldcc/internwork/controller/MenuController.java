package kr.ldcc.internwork.controller;

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
    public Response<Object> createMenu(@RequestBody @Valid MenuRequest request) {
        return Response.ok().setData(menuService.createMenu(request));
    }

    @GetMapping("/menu")
    public Response<Object> getMenuList() {
        return Response.ok().setData(menuService.getMenuList());
    }

    @GetMapping("/menu/{menuId}")
    public Response<Object> getDetailMenu(@PathVariable("menuId") Long menuId) {
        return Response.ok().setData(menuService.getDetailMenu(menuId));
    }

    @PutMapping("/menu/{menuId}")
    public Response<Object> updateMenu(@PathVariable("menuId") Long menuId, @RequestBody @Valid MenuRequest request) {
        return Response.ok().setData(menuService.updateMenu(menuId, request));
    }

    @DeleteMapping("/menu/{menuId}")
    public Response<Object> deleteMenu(@PathVariable("menuId") Long menuId) {
        menuService.deleteMenu(menuId);
        return Response.ok();
    }
}
