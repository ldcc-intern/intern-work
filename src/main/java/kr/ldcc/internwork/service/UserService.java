package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
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
    public Long createUser(UserRequest.CreateUserRequest request) {
        User user = User.builder().name(request.getName()).build();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new InternWorkException.dataDuplicateException(
                    "createUser Exception : "
                            + ExceptionCode.DATA_DUPLICATE_EXCEPTION.getMessage() + " : "
                            + e.getMessage());
        }
        return user.getId();
    }

    @Transactional
    public List<UserDto.GetUserListResponse> getUserList() {
        return UserMapper.toGetUserListResponse(userRepository.findAll());
    }

    @Transactional
    public UserDto.UpdateUserResponse updateUser(Long userId, UserRequest.UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new InternWorkException.dataNotFoundException(
                    "updateUser Exception : [존재하지 않는 User ID] : "
                            + ExceptionCode.DATA_NOT_FOUND_EXCEPTION.getMessage()
            );
        });
        user.updateUserName(request.getName() != null ? request.getName() : user.getName());
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new InternWorkException.dataUpdateException(
                    "updateUser Exception : "
                            + ExceptionCode.DATA_UPDATE_EXCEPTION.getMessage() + " : "
                            + e.getMessage());
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
        throw new InternWorkException.dataDeleteException(
                "deleteUser Exception : [존재하지 않는 User ID] : "
                        + ExceptionCode.DATA_DELETE_EXCEPTION.getMessage());
    }
}
