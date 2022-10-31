package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.model.dto.UserDto;
import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.model.mapper.UserMapper;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long createUser(UserRequest.CreateUserRequest createUserRequest) {
        User user = User.builder().name(createUserRequest.getName()).build();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("createUser Exception : {}", e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return user.getId();
    }

    @Transactional
    public List<UserDto.GetUserListResponse> getUserList() {
        return UserMapper.toGetUserListResponse(userRepository.findAll());
    }

    @Transactional
    public UserDto.UpdateUserResponse updateUser(Long userId, UserRequest.UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("updateUser Exception : [존재하지 않는 User ID]");
            return new InternWorkException.dataNotFoundException();
        });
        user.updateUserName(updateUserRequest.getName() != null ? updateUserRequest.getName() : user.getName());
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("updateUser Exception : {}", e.getMessage());
            throw new InternWorkException.dataUpdateException();
        }
        return UserMapper.toUpdateUserResponse(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
            return;
        }
        throw new InternWorkException.dataNotFoundException();
    }
}
