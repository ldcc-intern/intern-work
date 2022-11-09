package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.model.dto.UserDto;
import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public Response<Long> createUser(@RequestBody @Valid UserRequest request) {
        return Response.<Long>ok().setData(userService.createUser(request));
    }

    @GetMapping("/user")
    public Response<List<UserDto>> getUserList() {
        return Response.<List<UserDto>>ok().setData(userService.getUserList());
    }

    @PutMapping("/user/{userId}")
    public Response<UserDto> updateUser(@PathVariable("userId") Long userId, @RequestBody @Valid UserRequest request) {
        return Response.<UserDto>ok().setData(userService.updateUser(userId, request));
    }

    @DeleteMapping("/user/{userId}")
    public Response<Object> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return Response.ok();
    }
}
