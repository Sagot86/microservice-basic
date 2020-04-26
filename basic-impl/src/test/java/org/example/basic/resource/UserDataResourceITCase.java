package org.example.basic.resource;

import static org.example.basic.util.Constants.COUNTRY;
import static org.example.basic.util.Constants.MONEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.basic.IntegrationTest;
import org.example.basic.dto.UserDto;
import org.example.basic.dto.UserDtoFull;
import org.example.basic.model.User;
import org.example.basic.model.UserActivity;
import org.example.basic.repository.ActivityRepository;
import org.example.basic.repository.UserRepository;
import org.example.basic.util.DBObjectsFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * IT Case for {@link UserDataResource}.
 *
 * @author Igor_Orlov
 */
@IntegrationTest
@RunWith(SpringRunner.class)
public class UserDataResourceITCase {

    private static final String CREATE_USER_URL = "/user/create";
    private static final String UPDATE_USER_URL = "/user/update";
    private static final String GET_USER_INFO_URL = "/user/info";
    private static final String CREATE_ACTIVITY_URL = "/user/activity";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;

    @Before
    public void setUp() {
        prepareTestData();
    }

    @Test
    public void testCreateNewUser() throws Exception {
        String userId = mockMvc.perform(post(CREATE_USER_URL)
                .param(User.$.country, COUNTRY))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UUID uuid = objectMapper.readValue(userId, UUID.class);

        String result = mockMvc.perform(get(GET_USER_INFO_URL)
                .param(User.$.uid, uuid.toString()))
                .andReturn().getResponse().getContentAsString();

        UserDtoFull myUser = objectMapper.readValue(result, UserDtoFull.class);
        assertNotNull(myUser);
        assertEquals(COUNTRY, myUser.getCountry());
    }

    @Test
    public void testCreateUserActivity() throws Exception {
        List<Long> activities = Arrays.asList(1L, 100L, 1000L);

        for (Long activity : activities) {
            mockMvc.perform(post(CREATE_ACTIVITY_URL)
                    .param(UserActivity.$.uid, userId.toString())
                    .param(UserActivity.$.activity, activity.toString()))
                    .andExpect(status().isOk());
        }

        List<Long> savedActivities = activityRepository.findAll().stream()
                .map(UserActivity::getActivity)
                .collect(Collectors.toList());

        assertTrue(savedActivities.containsAll(activities));
    }

    @Test
    public void testUpdateUserInfo() throws Exception {
        Long money = 1L;
        UserDto userDto = DBObjectsFactory.getNewUserDto()
                .setUuid(userId)
                .setMoney(money);

        mockMvc.perform(put(UPDATE_USER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        User user = userRepository.findById(userId).get();
        assertEquals(money, user.getMoney());
    }

    @Test
    public void testGetUserInfo() throws Exception {
        String result = mockMvc.perform(get(GET_USER_INFO_URL)
                .param(User.$.uid, userId.toString()))
                .andReturn().getResponse().getContentAsString();

        UserDtoFull userDto = objectMapper.readValue(result, UserDtoFull.class);

        assertNotNull(userDto);
        assertEquals(MONEY, userDto.getMoney());
        assertEquals(COUNTRY, userDto.getCountry());
    }

    private void prepareTestData() {
        userId = userRepository.save(DBObjectsFactory.getNewUser()
                .setCountry(COUNTRY)
        ).getUid();
    }

}
