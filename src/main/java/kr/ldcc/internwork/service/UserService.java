package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException.DataDeleteException;
import kr.ldcc.internwork.common.exception.InternWorkException.DataDuplicateException;
import kr.ldcc.internwork.common.exception.InternWorkException.DataNotFoundException;
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

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserRequest request) {
        User user = User.builder().name(request.getName()).build();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("createUser Exception", ExceptionCode.DATA_DUPLICATE_EXCEPTION, e.getMessage());
            throw new DataDuplicateException(ExceptionCode.DATA_DUPLICATE_EXCEPTION);
        }
    }

    public List<UserDto> getUserList() {
        return UserDto.buildUserList(userRepository.findAll());
    }

    public void updateUser(Long userId, UserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("updateUser Exception | [존재하지 않는 User ID : " + userId + "]", ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
            return new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND_EXCEPTION);
        });
        user.updateUserName(request.getName() != null ? request.getName() : user.getName());
    }

    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
            return;
        }
        log.error("deleteUser Exception | [존재하지 않는 User ID : " + userId + "]", ExceptionCode.DATA_DELETE_EXCEPTION);
        throw new DataDeleteException(ExceptionCode.DATA_DELETE_EXCEPTION);
    }
}
