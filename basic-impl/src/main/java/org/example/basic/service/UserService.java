package org.example.basic.service;

import org.example.basic.dto.UserDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.exception.exceptions.UserNotFoundException;
import org.example.basic.mapper.UserMapper;
import org.example.basic.model.User;
import org.example.basic.model.UserActivity;
import org.example.basic.repository.ActivityRepository;
import org.example.basic.repository.UserRepository;

import com.google.common.annotations.VisibleForTesting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * UserService.
 *
 * @author Igor_Orlov
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final ActivityRepository activityRepository;
    private final UserRepository repository;
    private final UserMapper mapper;


    public UUID createUser(String country) {
        return repository.save(getNewUser(country)).getUid();
    }

    @Transactional
    public void updateUserMoney(UserDto dto) {
        UUID uid = dto.getUuid();
        Optional<User> user = repository.findById(uid);

        if (user.isPresent()) {
            user.get().setMoney(dto.getMoney());
        } else {
            throw new UserNotFoundException(uid.toString());
        }
    }

    public UserDtoFull getUserByUid(UUID uid) {
        Optional<User> user = repository.findById(uid);
        if (user.isPresent()) {
            return mapper.mapToFullUserDto(user.get());
        } else {
            throw new UserNotFoundException(uid.toString());
        }
    }

    public void createUsersActivity(UUID uid, Long activity) {
        activityRepository.save(constructActivity(uid, activity));
    }

    @VisibleForTesting
    UserActivity constructActivity(UUID uid, Long activity) {
        return new UserActivity()
                .setActivity(activity)
                .setUid(uid);
    }

    private User getNewUser(String country) {
        return new User()
                .setCountry(country)
                .setMoney(0L)
                .setActivities(Collections.emptyList());
    }
}
