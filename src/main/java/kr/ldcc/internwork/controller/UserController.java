package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public Response createUser(@RequestBody @Valid UserRequest request) {
        log.info("[createUser]");
        userService.createUser(request);
        return Response.ok();
    }

    @GetMapping
    public Response getUserList() {
        log.info("[getUserList]");
        return Response.ok().setData(userService.getUserList());
    }

    @PutMapping("/{userId}")
    public Response updateUser(@PathVariable("userId") Long userId, @RequestBody @Valid UserRequest request) {
        log.info("[updateUser]");
        userService.updateUser(userId, request);
        return Response.ok();
    }

    @DeleteMapping("/{userId}")
    public Response deleteUser(@PathVariable("userId") Long userId) {
        log.info("[deleteUser]");
        userService.deleteUser(userId);
        return Response.ok();
    }
}
