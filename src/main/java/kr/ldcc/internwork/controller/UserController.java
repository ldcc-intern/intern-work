package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public Response createUser(@RequestBody @Valid UserRequest request) {
        return Response.ok().setData(userService.createUser(request));
    }

    @GetMapping("/user")
    public Response getUserList() {
        return Response.ok().setData(userService.getUserList());
    }

    @PutMapping("/user/{userId}")
    public Response updateUser(@PathVariable("userId") Long userId, @RequestBody @Valid UserRequest request) {
        return Response.ok().setData(userService.updateUser(userId, request));
    }

    @DeleteMapping("/user/{userId}")
    public Response deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return Response.ok();
    }
}
