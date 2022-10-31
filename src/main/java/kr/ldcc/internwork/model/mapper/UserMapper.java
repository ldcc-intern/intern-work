package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.UserDto;
import kr.ldcc.internwork.model.dto.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static Response toCreateUserResponse(UserDto.CreateUserResponse createUserResponse) {
        return Response.ok().setData(createUserResponse);
    }

    public static Response toGetUserListResponse(List<UserDto.GetUserListResponse> userListResponses) {
        return Response.ok().setData(userListResponses);
    }

    public static Response toUpdateUserResponse(UserDto.UpdateUserResponse updateUserResponse) {
        return Response.ok().setData(updateUserResponse);
    }

    public static Response toDeleteUserResponse(UserDto.DeleteUserResponse deleteUserResponse) {
        return Response.ok().setData(deleteUserResponse);
    }
}
