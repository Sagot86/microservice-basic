package org.example.basic.mapper;

import static org.example.basic.util.Constants.ACTIVITY;
import static org.example.basic.util.Constants.USER_ID;
import static org.junit.Assert.assertEquals;

import org.example.basic.dto.UserActivityDto;
import org.example.basic.model.UserActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Unit tests for {@link ActivityMapper}.
 *
 * @author Igor_Orlov
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityMapperTest {

    private static final Date ACTIVITY_DATE = Date.from(Instant.now());

    private final ActivityMapper mapper = new ActivityMapper();

    @Test
    public void testMapToDto() {
        UserActivity activity = new UserActivity()
                .setActivity(ACTIVITY)
                .setActivityDate(ACTIVITY_DATE)
                .setUserId(USER_ID);

        UserActivityDto result = mapper.mapToActivityDto(activity);

        assertEquals(activity.getActivity(), result.getActivity());
        assertEquals(activity.getActivityDate(), result.getActivityDate());
        assertEquals(activity.getUserId(), result.getUid());
    }

    @Test
    public void testMapToDtosList() {
        UserActivity activity = new UserActivity()
                .setActivity(ACTIVITY)
                .setActivityDate(ACTIVITY_DATE)
                .setUserId(USER_ID);
        List<UserActivity> activities = new ArrayList<>(Arrays.asList(activity, activity, activity));

        List<UserActivityDto> result = mapper.mapToActivityDto(activities);

        for (UserActivityDto dto : result) {
            assertEquals(activity.getActivity(), dto.getActivity());
            assertEquals(activity.getActivityDate(), dto.getActivityDate());
            assertEquals(activity.getUserId(), dto.getUid());
        }
    }
}
