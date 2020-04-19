package org.example.basic.service;

import org.example.basic.dto.CountryStatDto;
import org.example.basic.dto.UserActivityDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.mapper.ActivityMapper;
import org.example.basic.mapper.UserMapper;
import org.example.basic.model.User;
import org.example.basic.repo.UserActivityRepository;
import org.example.basic.repo.UserRepository;

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
    private final UserActivityRepository activityRepository;
    private final UserMapper userMapper;
    private final ActivityMapper activityMapper;

    public List<UserDtoFull> getUsersByCountry(Integer usersCount, String country) {
        Pageable getTopRichUsers = PageRequest.of(0, usersCount, Sort.by(country).descending());
        List<User> users = userRepository.findAllByCountry(country, getTopRichUsers);

        if (users != null) {
            return userMapper.mapToFullUserDto(users);
        } else {
            return Collections.emptyList();
        }
    }

    public List<CountryStatDto> countNewUsersInPeriod(Date start, Date end) {
        return userRepository.countNewUsersByCounty(start, end);
    }

    public List<UserActivityDto> getUserActivitiesInPeriod(UUID userId, Date start, Date end) {
        return activityMapper.mapToActivityDto(activityRepository.findAllByUserIdAndActivityDateBetween(userId, start, end));
    }


}
