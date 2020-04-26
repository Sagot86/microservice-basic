package org.example.basic.resource;

import static org.example.basic.util.Constants.COUNTRY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.basic.IntegrationTest;
import org.example.basic.model.User;
import org.example.basic.model.UserActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

/**
 * UserDataResourceITCase.
 *
 * @author Igor_Orlov
 */
@IntegrationTest
@RunWith(SpringRunner.class)
public class UserDataResourceITCase {

    private static final String CREATE_URL = "/user/create";
    private static final String UPDATE_URL = "/user/update";
    private static final String GET_INFO_URL = "/user/info";
    private static final String ACTIVITY_URL = "/user/activity";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUserCreation() throws Exception {

        String uid = mockMvc.perform(post(CREATE_URL)
                .param(User.$.country, COUNTRY))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UUID uuid = objectMapper.readValue(uid, UUID.class);

        mockMvc.perform(post(ACTIVITY_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(UserActivity.$.uid, uuid.toString())
                .param(UserActivity.$.activity, "111"))
                .andExpect(status().isOk());

        mockMvc.perform(post(ACTIVITY_URL)
                .param(UserActivity.$.activity, "555")
                .param(UserActivity.$.uid, uuid.toString()))
                .andExpect(status().isOk());

        String result = mockMvc.perform(get(GET_INFO_URL)
                .param(User.$.uid, uuid.toString()))
                .andReturn().getResponse().getContentAsString();

        result.toString();


    }


}
