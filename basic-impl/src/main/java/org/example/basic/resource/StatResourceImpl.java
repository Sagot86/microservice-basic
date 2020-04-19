package org.example.basic.resource;

import org.example.basic.dto.CountryStatDto;
import org.example.basic.dto.UserActivityDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.service.StatService;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * StatisticResourceImpl.
 *
 * @author Igor_Orlov
 */
@Component
@RequiredArgsConstructor
public class StatResourceImpl implements StatResource {

    private final StatService statService;

    @Override
    public List<UserDtoFull> getTopUsersByCountry(@RequestParam Integer usersCount,
                                                  @RequestParam String country) {
        return statService.getUsersByCountry(usersCount, country);
    }

    @Override
    public List<CountryStatDto> countNewUsersInPeriod(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        return statService.countNewUsersInPeriod(start, end);
    }

    @Override
    public List<UserActivityDto> getActivities(@RequestParam UUID uid,
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        return statService.getUserActivitiesInPeriod(uid, start, end);
    }
}
