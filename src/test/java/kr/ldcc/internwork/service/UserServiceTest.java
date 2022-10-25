package kr.ldcc.internwork.service;

import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser() {
//        given
        UserRequest.CreateUserRequest createUserRequest = new UserRequest.CreateUserRequest().setName("이름");
//        when
        Long newUserId = userService.createUser(createUserRequest);
//        then
        User findUser = userRepository.findById(newUserId).get();
        assertEquals(createUserRequest.getName(), findUser.getName());
    }

    @Test
    void updateUser() {
//        given
        UserRequest.CreateUserRequest createUserRequest = new UserRequest.CreateUserRequest().setName("이름");
        Long userId = userService.createUser(createUserRequest);
        UserRequest.UpdateUserRequest updateUserRequest = new UserRequest.UpdateUserRequest();
        updateUserRequest.setName("테스트");
//        when
        User updateUser = userService.updateUser(userId, updateUserRequest);
//        then
        User findUser = userRepository.findById(userId).get();
        assertEquals(updateUser.getId(), findUser.getId());
        assertEquals(updateUser.getName(), findUser.getName());
    }

    @Test
    void deleteUser() {
//        given
        UserRequest.CreateUserRequest createUserRequest = new UserRequest.CreateUserRequest().setName("이름");
        Long userId = userService.createUser(createUserRequest);
//        when
        userService.deleteUser(userId);
//        then
        Boolean deleteUser = userRepository.findById(userId).isPresent();
        assertEquals(deleteUser, false);
    }
}