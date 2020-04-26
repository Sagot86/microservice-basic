package org.example.basic.util;

import static org.example.basic.util.Constants.ACTIVITY;
import static org.example.basic.util.Constants.COUNTRY;
import static org.example.basic.util.Constants.MONEY;
import static org.example.basic.util.Constants.NOW;

import org.example.basic.dto.UserDto;
import org.example.basic.model.User;
import org.example.basic.model.UserActivity;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.UUID;

/**
 * DBTestObjectsFactory.
 *
 * @author Igor_Orlov
 */
@UtilityClass
public class DBObjectsFactory {

    public User getNewUser() {
        return new User()
                .setCountry(COUNTRY)
                .setMoney(MONEY)
                .setActivities(Collections.emptyList());
    }

    public UserDto getNewUserDto() {
        return new UserDto()
                .setCountry(COUNTRY)
                .setMoney(MONEY);
    }

    public UserActivity getActivityForUser(UUID uuid) {
        return new UserActivity()
                .setUid(uuid)
                .setActivityDate(NOW)
                .setActivity(ACTIVITY);
    }
}
