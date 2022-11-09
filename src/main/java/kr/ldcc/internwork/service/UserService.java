package kr.ldcc.internwork.service;

import kr.ldcc.internwork.common.exception.InternWorkException;
import kr.ldcc.internwork.model.dto.UserDto;
import kr.ldcc.internwork.model.dto.request.UserRequest;
import kr.ldcc.internwork.model.entity.User;
import kr.ldcc.internwork.model.mapper.UserMapper;
import kr.ldcc.internwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long createUser(UserRequest request) {
        User user = User.builder().name(request.getName()).build();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("createUser Exception | " + e.getMessage());
            throw new InternWorkException.dataDuplicateException();
        }
        return user.getId();
    }

    @Transactional
    public List<UserDto> getUserList() {
        return UserMapper.toGetUserListResponse(userRepository.findAll());
    }

    @Transactional
    public UserDto updateUser(Long userId, UserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("updateUser Exception | [존재하지 않는 User ID : " + userId + "]");
            return new InternWorkException.dataNotFoundException();
        });
        user.updateUserName(request.getName() != null ? request.getName() : user.getName());
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("updateUser Exception | " + e.getMessage());
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
        log.error("deleteUser Exception | [존재하지 않는 User ID : " + userId + "]");
        throw new InternWorkException.dataDeleteException();
    }
}
