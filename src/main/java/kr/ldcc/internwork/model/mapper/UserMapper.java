package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.UserDto;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static UserDto.CreateUserResponse toCreateUserResponse(Long userId) {
        return new UserDto.CreateUserResponse().setId(userId);
    }

    public static List<User> toGetUserListResponse(List<User> users) {
        return users;
    }

    public static UserDto.UpdateUserResponse toUpdateUserResponse(User user) {
        return new UserDto.UpdateUserResponse().setId(user.getId()).setName(user.getName());
    }

    public static Response toDeleteUserResponse() {
        return Response.ok();
    }
}
