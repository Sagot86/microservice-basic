package org.example.basic.resource;

import org.example.basic.dto.UserDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * UserDataResourceImpl.
 *
 * @author Igor_Orlov
 */
@Component
@RequiredArgsConstructor
public class UserDataResourceImpl implements UserDataResource {

    private final UserService userService;

    @Override
    public void updateUser(UserDto userDto) {
        userService.updateUser(userDto);
    }

    @Override
    public UserDtoFull getUserInfo(UUID uid) {
        return userService.getUserByUid(uid);
    }

    @Override
    public void updateActivity(UUID uid, Long activity) {
        userService.updateUsersActivity(uid, activity);
    }
}
