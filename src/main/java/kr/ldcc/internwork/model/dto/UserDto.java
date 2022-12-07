package kr.ldcc.internwork.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.ldcc.internwork.model.entity.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserDto {
    private final Long id;
    private final String name;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public static List<UserDto> buildUserList(List<User> userList) {
        return userList.stream().map(UserDto::new).collect(Collectors.toList());
    }
}
