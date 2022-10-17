package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.UserDto;
import kr.ldcc.internwork.model.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static Object toUserListResponse(List<User> users) {
        List<UserDto.UserListResponse> userListResonps = new ArrayList<>();
        users.stream().forEach(user -> userListResonps.add(new UserDto.UserListResponse().setId(user.getId()).setName(user.getName())));
        return userListResonps;
    }

    public static UserDto toUserUpdateResponse(User user) {
        return ObjectMapperUtils.map(user, UserDto.class);
    }
}
