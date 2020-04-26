package org.example.basic.resource;

import org.example.basic.dto.UserDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * UserDataResourceImpl.
 *
 * @author Igor_Orlov
 */
@RestController
@RequiredArgsConstructor
public class UserDataResourceImpl implements UserDataResource {

    private final UserService userService;

    @Override
    public UUID createNewUser(String country) {
        return userService.createUser(country);
    }

    @Override
    public void updateUser(UserDto userDto) {
        userService.updateUserMoney(userDto);
    }

    @Override
    public UserDtoFull getUserInfo(UUID uid) {
        return userService.getUserByUid(uid);
    }

    @Override
    public void createActivity(UUID uid, Long activity) {
        userService.createUsersActivity(uid, activity);
    }
}
