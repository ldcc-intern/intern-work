package kr.ldcc.internwork.model.entity;

import kr.ldcc.internwork.controller.UserController;
import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.repository.FaqRepository;
import kr.ldcc.internwork.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
class UserTest {

    @Autowired UserRepository userRepository;

    @Autowired UserController userController;


    @BeforeEach
    public void before() {
        User user = User.builder()
                .name("name")
                .build();
        userRepository.save(user);
    }

    @Test
    public void 사용자등록() throws Exception {
        UserRequest.CreateUserRequest createUserRequest = new UserRequest.CreateUserRequest();

        createUserRequest.setName("사용자1");


        Response user = userController.createUser(createUserRequest);

    }

}