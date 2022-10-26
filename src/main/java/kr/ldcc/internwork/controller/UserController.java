package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.mapper.UserMapper;
import kr.ldcc.internwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public Response createUser(@RequestBody @Valid UserRequest.CreateUserRequest createUserRequest) {
        return Response.ok().setData(UserMapper.toCreateUserResponse(userService.createUser(createUserRequest)));
    }

    @GetMapping("/user")
    public Response getUserList() {
        return Response.ok().setData(UserMapper.toGetUserListResponse(userService.getUserList()));
    }

    @PutMapping("/user/{userId}")
    public Response updateUser(@PathVariable("userId") Long userId, @RequestBody @Valid UserRequest.UpdateUserRequest updateUserRequest) {
        return Response.ok().setData(UserMapper.toUpdateUserResponse(userService.updateUser(userId, updateUserRequest)));
    }

    @DeleteMapping("/user/{userId}")
    public Response deleteUser(@PathVariable("userId") Long userId) {
        return Response.ok().setData(UserMapper.toDeleteUserResponse(userService.deleteUser(userId)));
    }
}
