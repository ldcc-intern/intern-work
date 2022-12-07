package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.model.dto.request.MenuRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/menu")
@Slf4j
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping
    public Response createMenu(@RequestBody @Valid MenuRequest request) {
        log.info("[createMenu]");
        menuService.createMenu(request);
        return Response.ok();
    }

    @GetMapping
    public Response getMenuList() {
        log.info("[getMenuList]");
        return Response.ok().setData(menuService.getMenuList());
    }

    @GetMapping("/{menuId}")
    public Response getDetailMenu(@PathVariable("menuId") Long menuId) {
        log.info("[getDetailMenu]");
        return Response.ok().setData(menuService.getDetailMenu(menuId));
    }

    @PutMapping("/{menuId}")
    public Response updateMenu(@PathVariable("menuId") Long menuId, @RequestBody @Valid MenuRequest request) {
        log.info("[updateMenu]");
        menuService.updateMenu(menuId, request);
        return Response.ok();
    }

    @DeleteMapping("/{menuId}")
    public Response deleteMenu(@PathVariable("menuId") Long menuId) {
        log.info("[deleteMenu]");
        menuService.deleteMenu(menuId);
        return Response.ok();
    }
}
