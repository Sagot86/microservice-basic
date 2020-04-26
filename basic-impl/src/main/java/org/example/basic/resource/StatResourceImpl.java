package org.example.basic.resource;

import org.example.basic.dto.CountryStatDto;
import org.example.basic.dto.UserActivityDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.service.StatService;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * StatisticResourceImpl.
 *
 * @author Igor_Orlov
 */
@RestController
@RequiredArgsConstructor
public class StatResourceImpl implements StatResource {

    private final StatService statService;

    @Override
    public List<UserDtoFull> getTopUsersByCountry(@RequestParam Integer usersCount,
                                                  @RequestParam String country) {
        return statService.getTopRichInCountry(usersCount, country);
    }

    @Override
    public List<CountryStatDto> countNewUsersInPeriod(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        return statService.countNewUsersInPeriod(start, end);
    }

    @Override
    public List<UserActivityDto> getActivities(@RequestParam UUID uid,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        return statService.getUserActivitiesInPeriod(uid, start, end);
    }
}
