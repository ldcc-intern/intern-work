package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.UserDto;
import kr.ldcc.internwork.model.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static UserDto.CreateUserResponse toCreateUserResponse(User user) {
        return new UserDto.CreateUserResponse().setId(user.getId());
    }

    public static List<UserDto.GetUserListResponse> toGetUserListResponse(List<User> users) {
        return users.stream().map(user -> UserDto.GetUserListResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build()).collect(Collectors.toList());
    }

    public static UserDto.UpdateUserResponse toUpdateUserResponse(User user) {
        return new UserDto.UpdateUserResponse().setId(user.getId()).setName(user.getName());
    }

    public static UserDto.DeleteUserResponse toDeleteUserResponse(User user) {
        return new UserDto.DeleteUserResponse().setId(user.getId());
    }
}
