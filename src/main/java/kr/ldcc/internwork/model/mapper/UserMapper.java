package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.UserDto;
import kr.ldcc.internwork.model.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static List<UserDto> toGetUserListResponse(List<User> users) {
        return users.stream().map(user -> UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build()).collect(Collectors.toList());
    }

    public static UserDto toUpdateUserResponse(User user) {
        return new UserDto().setId(user.getId()).setName(user.getName());
    }
}
