package kr.ldcc.internwork.controller;

import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public Response createUser(@RequestBody UserRequest.CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @GetMapping("/user")
    public Response getUserList() {
        return userService.getUserList();
    }

    @PutMapping("/user/{userId}")
    public Response updateUser(@PathVariable("userId") Long userId, @RequestBody UserRequest.UpdateUserRequest updateUserRequest) {
        return userService.updateUser(userId, updateUserRequest);
    }

    @DeleteMapping("/user/{userId}")
    public Response deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUser(userId);
    }
}
