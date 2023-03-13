package ru.practicum.emw.main.user.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.user.entity.User;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserDto toUserDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());

        dto.setName(user.getName());

        dto.setEmail(user.getEmail());

        return dto;
    }

    public static List<UserDto> toUsersDto(List<User> users) {
        return users
                .stream()
                .map(UserMapper::toUserDto)
                .collect(toList());
    }

    public static UserShortDto toUserShortDto(User user) {
        UserShortDto dto = new UserShortDto();

        dto.setId(user.getId());

        dto.setName(user.getName());

        return dto;
    }

}
