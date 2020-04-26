package org.example.basic.resource;

import static org.example.basic.util.Constants.NOW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.basic.IntegrationTest;
import org.example.basic.dto.CountryStatDto;
import org.example.basic.dto.UserActivityDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.model.User;
import org.example.basic.repository.ActivityRepository;
import org.example.basic.repository.UserRepository;
import org.example.basic.util.DBObjectsFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * IT Case for {@link StatResource}.
 *
 * @author Igor_Orlov
 */
@IntegrationTest
@RunWith(SpringRunner.class)
public class StatResourceITCase {

    private static final String SELECT_TOP_URL = "/statistic/money";
    private static final String SELECT_NEW_USERS_IN_PERIOD_URL = "/statistic/new_users";
    private static final String GET_ACTIVITIES_BY_UID_URL = "/statistic/activities";
    private static final String COUNTRY_ONE = "countryOne";
    private static final String COUNTRY_TWO = "countryTwo";
    private static final Long BRONZE = 3L;
    private static final Long SILVER = 300L;
    private static final Long GOLD = 3000L;
    private static final LocalDateTime START_DATE = NOW.minusMinutes(20);
    private static final LocalDateTime END_DATE = NOW.minusMinutes(10);

    private final List<User> richUsersCountryOne = new ArrayList<>();
    private UUID userId;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        prepareTestData();
    }

    @Test
    public void testSelectTop2Rich() throws Exception {
        String result = mockMvc.perform(get(SELECT_TOP_URL)
                .param(User.$.country, COUNTRY_ONE)
                .param("usersCount", "2"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Map<UUID, Long> richUserMap = Arrays.stream(mapper.readValue(result, UserDtoFull[].class))
                .collect(Collectors.toMap(UserDtoFull::getUuid, UserDtoFull::getMoney));

        for (User user : richUsersCountryOne) {
            Long money = richUserMap.get(user.getUid());
            assertNotNull(money);
            assertEquals(money, user.getMoney());
        }
    }

    @Test
    public void testSelectNewUsersInPeriod() throws Exception {
        String result = mockMvc.perform(get(SELECT_NEW_USERS_IN_PERIOD_URL)
                .param("start", START_DATE.toString())
                .param("end", END_DATE.toString()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Map<String, Long> counties = Arrays.stream(mapper.readValue(result, CountryStatDto[].class))
                .collect(Collectors.toMap(CountryStatDto::getCountry, CountryStatDto::getUserCount));

        assertEquals(Long.valueOf(1), counties.get(COUNTRY_ONE));
        assertEquals(Long.valueOf(1), counties.get(COUNTRY_TWO));
    }

    @Test
    public void testGetActivitiesForUserInPeriod() throws Exception {
        String result = mockMvc.perform(get(GET_ACTIVITIES_BY_UID_URL)
                .param(User.$.uid, userId.toString())
                .param("start", START_DATE.toString())
                .param("end", END_DATE.toString()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<UserActivityDto> activities = Arrays.asList(mapper.readValue(result, UserActivityDto[].class));

        assertEquals(3, activities.size());
    }

    private void prepareTestData() {
        userId = userRepository.save(DBObjectsFactory
                .getNewUser()
                .setCreationDate(START_DATE.minusMinutes(1))
                .setCountry(COUNTRY_ONE)
                .setMoney(BRONZE)
        ).getUid();

        richUsersCountryOne.add(userRepository.save(DBObjectsFactory
                .getNewUser()
                .setCreationDate(START_DATE.plusMinutes(1))
                .setCountry(COUNTRY_ONE)
                .setMoney(SILVER)));

        richUsersCountryOne.add(userRepository.save(DBObjectsFactory
                .getNewUser()
                .setCountry(COUNTRY_ONE)
                .setMoney(GOLD)));

        userRepository.save(DBObjectsFactory
                .getNewUser()
                .setCreationDate(START_DATE.minusMinutes(1))
                .setCountry(COUNTRY_TWO)
                .setMoney(BRONZE));

        userRepository.save(DBObjectsFactory
                .getNewUser()
                .setCreationDate(START_DATE.plusMinutes(1))
                .setCountry(COUNTRY_TWO)
                .setMoney(SILVER));

        userRepository.save(DBObjectsFactory
                .getNewUser()
                .setCountry(COUNTRY_TWO)
                .setMoney(GOLD));

        activityRepository.saveAll(Arrays.asList(
                DBObjectsFactory.getActivityForUser(userId)
                        .setActivityDate(START_DATE.minusMinutes(1)),
                DBObjectsFactory.getActivityForUser(userId)
                        .setActivityDate(START_DATE.minusMinutes(2)),
                DBObjectsFactory.getActivityForUser(userId)
                        .setActivityDate(START_DATE.minusMinutes(3)),
                DBObjectsFactory.getActivityForUser(userId)
                        .setActivityDate(START_DATE.plusMinutes(1)),
                DBObjectsFactory.getActivityForUser(userId)
                        .setActivityDate(START_DATE.plusMinutes(2)),
                DBObjectsFactory.getActivityForUser(userId)
                        .setActivityDate(START_DATE.plusMinutes(3))
        ));
    }
}
