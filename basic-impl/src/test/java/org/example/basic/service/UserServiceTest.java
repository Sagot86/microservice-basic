package org.example.basic.service;

import static org.example.basic.util.Constants.ACTIVITY;
import static org.example.basic.util.Constants.USER_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.example.basic.dto.UserDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.exception.UserNotFoundException;
import org.example.basic.mapper.UserMapper;
import org.example.basic.model.User;
import org.example.basic.model.UserActivity;
import org.example.basic.repository.ActivityRepository;
import org.example.basic.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

/**
 * Unit tests for {@link UserService}.
 *
 * @author Igor_Orlov
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testExistsUserUpdating() {
        UserDto userDto = new UserDto()
                .setId(USER_ID)
                .setMoney(1L);
        User user = new User()
                .setUid(USER_ID);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        userService.updateUserMoney(userDto);

        verify(userRepository).findById(USER_ID);
        verifyNoMoreInteractions(userRepository);
    }

    @Test(expected = UserNotFoundException.class)
    public void testUserUpdatingWithNoUserExists() {
        UserDto userDto = new UserDto()
                .setId(USER_ID)
                .setMoney(1L);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        userService.updateUserMoney(userDto);

        verify(userRepository).findById(USER_ID);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGettingUserByUid() {
        UserDtoFull userDto = new UserDtoFull();
        User user = new User();

        when(mapper.mapToFullUserDto(user)).thenReturn(userDto);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        UserDtoFull result = userService.getUserByUid(USER_ID);

        verify(mapper).mapToFullUserDto(user);
        verify(userRepository).findById(USER_ID);
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(userRepository);
        assertNotNull(result);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGettingUserByUidWithNoUser() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        UserDtoFull result = userService.getUserByUid(USER_ID);

        verify(userRepository).findById(USER_ID);
        verifyNoMoreInteractions(userRepository);
        assertNull(result);
    }

    @Test
    public void testActivityUpdating() {
        UserActivity activity = userService.constructActivity(USER_ID, ACTIVITY);

        userService.createUsersActivity(USER_ID, ACTIVITY);

        verify(activityRepository).save(activity);
        verifyNoMoreInteractions(activityRepository);
    }

    @Test
    public void testConstructActivity() {
        UserActivity activity = userService.constructActivity(USER_ID, ACTIVITY);

        assertEquals(USER_ID, activity.getUid());
        assertEquals(ACTIVITY, activity.getActivity());
    }
}
