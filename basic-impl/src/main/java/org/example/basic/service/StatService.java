package org.example.basic.service;

import org.example.basic.dto.CountryStatDto;
import org.example.basic.dto.UserActivityDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.mapper.ActivityMapper;
import org.example.basic.mapper.UserMapper;
import org.example.basic.model.User;
import org.example.basic.repository.ActivityRepository;
import org.example.basic.repository.UserRepository;

import com.google.common.annotations.VisibleForTesting;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * StatService.
 *
 * @author Igor_Orlov
 */
@Service
@RequiredArgsConstructor
public class StatService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final UserMapper userMapper;
    private final ActivityMapper activityMapper;

    public List<UserDtoFull> getTopRichInCountry(Integer usersCount, String country) {
        Pageable topRichUsers = PageRequest.of(0, usersCount, Sort.by(User.$.money).descending());
        return getTopUsersInCountry(country, topRichUsers);
    }

    public List<CountryStatDto> countNewUsersInPeriod(Date start, Date end) {
        return userRepository.countNewUsersForEachCounty(start, end);
    }

    public List<UserActivityDto> getUserActivitiesInPeriod(UUID userId, Date start, Date end) {
        return activityMapper.mapToActivityDto(activityRepository.findAllByUidAndActivityDateBetween(userId, start, end));
    }

    @VisibleForTesting
    List<UserDtoFull> getTopUsersInCountry(String country, Pageable sortType) {
        List<User> users = userRepository.findAllByCountry(country, sortType);

        if (users != null) {
            return userMapper.mapToFullUserDto(users);
        } else {
            return Collections.emptyList();
        }
    }
}
