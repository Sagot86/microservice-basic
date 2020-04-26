package org.example.basic.service;

import static org.example.basic.util.Constants.COUNTRY;
import static org.example.basic.util.Constants.USER_ID;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.example.basic.dto.CountryStatDto;
import org.example.basic.dto.UserActivityDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.mapper.ActivityMapper;
import org.example.basic.mapper.UserMapper;
import org.example.basic.model.User;
import org.example.basic.repository.ActivityRepository;
import org.example.basic.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Unit tests for {@link StatService}.
 *
 * @author Igor_Orlov
 */
@RunWith(MockitoJUnitRunner.class)
public class StatServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private ActivityMapper activityMapper;

    @InjectMocks
    private StatService statService;

    @Test
    public void testGettingTop5RichUsersByCountry() {
        int usersCount = 5;
        Pageable topRichUsers = PageRequest.of(0, usersCount, Sort.by(User.$.money).descending());

        List<UserDtoFull> result = statService.getTopRichInCountry(usersCount, COUNTRY);

        assertNotNull(result);
        verify(userRepository).findAllByCountry(COUNTRY, topRichUsers);
        verify(userMapper).mapToFullUserDto(Collections.emptyList());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    public void testGettingTop5RichUsersByCountryWithZeroFounded() {
        int usersCount = 5;
        Pageable topRichUsers = PageRequest.of(0, usersCount, Sort.by(User.$.money).descending());

        when(userRepository.findAllByCountry(COUNTRY, topRichUsers)).thenReturn(null);

        List<UserDtoFull> result = statService.getTopRichInCountry(usersCount, COUNTRY);

        assertNotNull(result);
        verify(userRepository).findAllByCountry(COUNTRY, topRichUsers);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testCountNewUsers() {
        List<CountryStatDto> countryStatDtos = Collections.emptyList();
        Date start = Date.from(Instant.now());
        Date end = Date.from(Instant.now());

        when(userRepository.countNewUsersForEachCounty(start, end)).thenReturn(countryStatDtos);

        List<CountryStatDto> result = statService.countNewUsersInPeriod(start, end);

        assertNotNull(result);
        verify(userRepository).countNewUsersForEachCounty(start, end);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetUsersActivities() {
        Date start = Date.from(Instant.now());
        Date end = Date.from(Instant.now());

        List<UserActivityDto> result = statService.getUserActivitiesInPeriod(USER_ID, start, end);

        assertNotNull(result);
        verify(activityRepository).findAllByUidAndActivityDateBetween(USER_ID, start, end);
        verify(activityMapper).mapToActivityDto(Collections.emptyList());
        verifyNoMoreInteractions(activityRepository);
        verifyNoMoreInteractions(activityMapper);
    }

}
