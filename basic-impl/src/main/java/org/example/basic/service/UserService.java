package org.example.basic.service;

import org.example.basic.dto.UserDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.mapper.UserMapper;
import org.example.basic.model.UserActivity;
import org.example.basic.repository.UserActivityRepository;
import org.example.basic.repository.UserRepository;

import com.google.common.annotations.VisibleForTesting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * UserService.
 *
 * @author Igor_Orlov
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserActivityRepository activityRepository;
    private final UserRepository repository;
    private final UserMapper mapper;

    public void updateUser(UserDto dto) {
        repository.save(mapper.mapToUser(dto));
    }

    public UserDtoFull getUserByUid(UUID uid) {
        return mapper.mapToFullUserDto(repository.findUserByUid(uid));
    }

    public void updateUsersActivity(UUID uid, Long activity) {
        activityRepository.save(constructActivity(uid, activity));
    }

    @VisibleForTesting
    UserActivity constructActivity(UUID uid, Long activity) {
        return new UserActivity()
                .setActivity(activity)
                .setUserId(uid);
    }
}
