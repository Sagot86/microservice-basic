package org.example.basic.resource;

import org.example.basic.dto.CountryStatDto;
import org.example.basic.dto.UserActivityDto;
import org.example.basic.dto.UserDtoFull;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * StatisticResource.
 *
 * @author Igor_Orlov
 */
@RequestMapping("/statistic")
public interface StatResource {

    /**
     * Get top X most rich users by country.
     *
     * @param usersCount users count
     * @param country    country name
     */
    @GetMapping("/money")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success")
    })
    List<UserDtoFull> getTopUsersByCountry(@RequestParam Integer usersCount,
                                           @RequestParam String country);

    /**
     * Count all new users in set period.
     *
     * @param start period start
     * @param end   period end
     */
    @GetMapping("/new_users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success")
    })
    List<CountryStatDto> countNewUsersInPeriod(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end);

    /**
     * Get user activities in set period.
     *
     * @param uid   User id
     * @param start period start
     * @param end   period end
     */
    @GetMapping("/activities")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success")
    })
    List<UserActivityDto> getActivities(@RequestParam UUID uid,
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end);

}
