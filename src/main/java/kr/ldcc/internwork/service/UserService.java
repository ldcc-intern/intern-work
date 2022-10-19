package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.model.mapper.UserMapper;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Response createUser(UserRequest.CreateUserRequest createUserRequest) {
        User user = User.builder().name(createUserRequest.getName()).build();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("createUser Exception : {}", e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return UserMapper.toUserCreateResponse(user.getId());
    }

    @Transactional
    public Response getUserList() {
        List<User> users = userRepository.findAll();
        return UserMapper.toUserListResponse(users);
    }

    @Transactional
    public Response updateUser(Long userId, UserRequest.UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("updateUser Exception : [존재하지 않는 User ID]");
            return new InternWorkException.dataUpdateException();
        });
        user.updateUserName(updateUserRequest.getName() != null ? updateUserRequest.getName() : user.getName());
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("updateUser Exception : {}", e.getMessage());
            throw new InternWorkException.dataUpdateException();
        }
        return UserMapper.toUserUpdateResponse(user);
    }

    @Transactional
    public Response deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
            return UserMapper.toUserDeleteResponse();
        }
        throw new InternWorkException.dataNotFoundException();
    }
}
