package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.UserDto;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static Response toCreateUserResponse(Long userId) {
        return Response.ok().setData(new UserDto.CreateUserResponse().setId(userId));
    }

    public static Response toGetUserListResponse(List<User> users) {
        return Response.ok().setData(users);
    }

    public static Response toUpdateUserResponse(User user) {
        return Response.ok().setData(ObjectMapperUtils.map(user, UserDto.class));
    }

    public static Response toDeleteUserResponse() {
        return Response.ok();
    }
}
