package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.model.dto.UserDto;
import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto.CreateUserResponse createUser(UserRequest.CreateUserRequest createUserRequest) {
        User user = User.builder().name(createUserRequest.getName()).build();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("createUser Exception : {}", e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return new UserDto.CreateUserResponse().setId(user.getId());
    }

    @Transactional
    public List<UserDto.GetUserListResponse> getUserList() {
        return userRepository.findAll().stream().map(user -> UserDto.GetUserListResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build()).collect(Collectors.toList());
    }

    @Transactional
    public UserDto.UpdateUserResponse updateUser(Long userId, UserRequest.UpdateUserRequest updateUserRequest) {
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
        return new UserDto.UpdateUserResponse().setId(user.getId()).setName(user.getName());
    }

    @Transactional
    public UserDto.DeleteUserResponse deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
            return new UserDto.DeleteUserResponse().setId(user.get().getId());
        }
        throw new InternWorkException.dataNotFoundException();
    }
}
