package kr.ldcc.internwork.model.mapper;

import kr.ldcc.internwork.model.dto.UserDto;
import kr.ldcc.internwork.model.dto.response.Response;
import kr.ldcc.internwork.model.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static Response toUserCreateResponse(Long id) {
        UserDto.UserIdResponse userIdResponse = new UserDto.UserIdResponse();
        userIdResponse.setId(id);
        return Response.ok().setData(userIdResponse);
    }

    public static Response toUserListResponse(List<User> users) {
        List<UserDto.UserListResponse> userListResonps = new ArrayList<>();
        users.stream().forEach(user -> userListResonps.add(new UserDto.UserListResponse().setId(user.getId()).setName(user.getName())));
        return Response.ok().setData(userListResonps);
    }

    public static Response toUserUpdateResponse(User user) {
        return Response.ok().setData(ObjectMapperUtils.map(user, UserDto.class));
    }

    public static Response toUserDeleteResponse() {
        return Response.ok();
    }
}
