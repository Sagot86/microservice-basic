package org.example.basic.mapper;

import org.example.basic.dto.UserDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserMapper.
 *
 * @author Igor_Orlov
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ActivityMapper activityMapper;

    public User mapToUser(UserDto dto) {
        return new User()
                .setUid(dto.getUid())
                .setCountry(dto.getCountry())
                .setMoney(dto.getMoney());
    }

    public UserDtoFull mapToFullUserDto(User user) {
        return new UserDtoFull()
                .setUid(user.getUid())
                .setCountry(user.getCountry())
                .setMoney(user.getMoney())
                .setCreationDate(user.getCreationDate())
                .setActivities(activityMapper.mapToActivityDto(user.getActivities()));
    }

    public List<UserDtoFull> mapToFullUserDto(List<User> users) {
        return users.stream()
                .map(this::mapToFullUserDto)
                .collect(Collectors.toList());
    }

}
