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
        UserRequest.CreateUserRequest createUserRequest = new UserRequest.CreateUserRequest().setName("Test 이름");
//        when
        User newUser = userService.createUser(createUserRequest);
//        then
        User findUser = userRepository.findById(newUser.getId()).get();
        assertEquals(findUser.getName(), newUser.getName());
    }

    @Test
    void updateUser() {
//        given
        UserRequest.CreateUserRequest createUserRequest = new UserRequest.CreateUserRequest().setName("Test 이름");
        User user = userService.createUser(createUserRequest);
        UserRequest.UpdateUserRequest updateUserRequest = new UserRequest.UpdateUserRequest().setName("테스트 이름");
//        when
        User updateUser = userService.updateUser(user.getId(), updateUserRequest);
//        then
        User findUser = userRepository.findById(user.getId()).get();
        assertEquals(findUser.getName(), updateUser.getName());
    }

    @Test
    void deleteUser() {
//        given
        UserRequest.CreateUserRequest createUserRequest = new UserRequest.CreateUserRequest().setName("Test 이름");
        User user = userService.createUser(createUserRequest);
//        when
        User deleteUser = userService.deleteUser(user.getId());
//        then
        boolean findUser = userRepository.findById(user.getId()).isPresent();
        assertEquals(deleteUser.getId(), user.getId());
        assertEquals(findUser, false);
    }
}