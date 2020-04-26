package org.example.basic.mapper;

import static org.example.basic.util.Constants.USER_ID;
import static org.junit.Assert.assertEquals;

import org.example.basic.dto.UserActivityDto;
import org.example.basic.model.UserActivity;
import org.example.basic.util.DBObjectsFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for {@link ActivityMapper}.
 *
 * @author Igor_Orlov
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivityMapperTest {

    private final ActivityMapper mapper = new ActivityMapper();

    @Test
    public void testMapToDto() {
        UserActivity activity = DBObjectsFactory.getActivityForUser(USER_ID);

        UserActivityDto result = mapper.mapToActivityDto(activity);

        assertEquals(activity.getActivity(), result.getActivity());
        assertEquals(activity.getActivityDate(), result.getActivityDate());
        assertEquals(activity.getUid(), result.getUid());
    }

    @Test
    public void testMapToDtosList() {
        UserActivity activity = DBObjectsFactory.getActivityForUser(USER_ID);
        List<UserActivity> activities = new ArrayList<>(Arrays.asList(activity, activity, activity));

        List<UserActivityDto> result = mapper.mapToActivityDto(activities);

        for (UserActivityDto dto : result) {
            assertEquals(activity.getActivity(), dto.getActivity());
            assertEquals(activity.getActivityDate(), dto.getActivityDate());
            assertEquals(activity.getUid(), dto.getUid());
        }
    }
}
