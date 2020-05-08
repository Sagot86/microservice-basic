package org.example.basic.mapper;

import static org.example.basic.util.Constants.COUNTRY;
import static org.example.basic.util.Constants.MONEY;
import static org.example.basic.util.Constants.NOW;
import static org.example.basic.util.Constants.USER_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.example.basic.dto.UserActivityDto;
import org.example.basic.dto.UserDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.model.User;
import org.example.basic.model.UserActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Unit tests for {@link UserMapper}.
 *
 * @author Igor_Orlov
 */
@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    @Mock
    private ActivityMapper activityMapper;

    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void testMapToEntity() {
        UserDto userDto = new UserDto()
                .setUuid(USER_ID)
                .setMoney(MONEY)
                .setCountry(COUNTRY);

        User result = userMapper.mapToUser(userDto);

        assertEquals(userDto.getCountry(), result.getCountry());
        assertEquals(userDto.getMoney(), result.getMoney());
        assertEquals(userDto.getUuid(), result.getUid());
    }

    @Test
    public void testMapToFullDto() {
        List<UserActivity> activities = Collections.singletonList(new UserActivity());
        List<UserActivityDto> activityDtos = Collections.singletonList(new UserActivityDto());
        User user = new User()
                .setUid(USER_ID)
                .setMoney(MONEY)
                .setCountry(COUNTRY)
                .setActivities(activities)
                .setCreationDate(NOW);

        when(activityMapper.mapToActivityDto(activities)).thenReturn(activityDtos);

        UserDtoFull result = userMapper.mapToFullUserDto(user);

        assertEquals(user.getCountry(), result.getCountry());
        assertEquals(user.getMoney(), result.getMoney());
        assertEquals(user.getUid(), result.getUuid());
        assertEquals(user.getCreationDate(), result.getCreationDate());
        assertEquals(activityDtos, result.getActivities());
        verify(activityMapper).mapToActivityDto(activities);
        verifyNoMoreInteractions(activityMapper);
    }

    @Test
    public void testMapToFullDtosList() {
        List<UserActivity> activities = Collections.singletonList(new UserActivity());
        List<UserActivityDto> activityDtos = Collections.singletonList(new UserActivityDto());
        User user = new User()
                .setUid(USER_ID)
                .setMoney(MONEY)
                .setCountry(COUNTRY)
                .setActivities(activities)
                .setCreationDate(NOW);
        List<User> users = new ArrayList<>(Arrays.asList(user, user, user));

        when(activityMapper.mapToActivityDto(activities)).thenReturn(activityDtos);

        List<UserDtoFull> result = userMapper.mapToFullUserDto(users);

        for (UserDtoFull dto : result) {
            assertEquals(user.getCountry(), dto.getCountry());
            assertEquals(user.getMoney(), dto.getMoney());
            assertEquals(user.getUid(), dto.getUuid());
            assertEquals(user.getCreationDate(), dto.getCreationDate());
            assertEquals(activityDtos, dto.getActivities());
        }

        verify(activityMapper, times(3)).mapToActivityDto(activities);
        verifyNoMoreInteractions(activityMapper);
    }
}
