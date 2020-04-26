package org.example.basic.repository;

import static org.example.basic.util.Constants.ACTIVITY;
import static org.example.basic.util.Constants.NOW;
import static org.junit.Assert.assertEquals;

import org.example.basic.IntegrationTest;
import org.example.basic.model.User;
import org.example.basic.model.UserActivity;
import org.example.basic.util.DBObjectsFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * IT Case for {@link ActivityRepository}.
 *
 * @author Igor_Orlov
 */
@IntegrationTest
@RunWith(SpringRunner.class)
public class ActivityRepositoryITCase {

    @Autowired
    private ActivityRepository repository;

    @Autowired
    private UserRepository userRepository;

    private UUID userId;

    @Before
    public void saveUser() {
        userId = userRepository.save(new User()).getUid();
    }

    @Test
    public void testSavingNewActivity() {
        Long activityValue = 2L;
        LocalDateTime start = NOW.minusDays(3);
        LocalDateTime end = NOW.minusDays(1);

        UserActivity oldActivity = repository.save(DBObjectsFactory
                .getActivityForUser(userId)
                .setActivityDate(start.minusDays(1)));
        UserActivity middleActivityOne = repository.save(DBObjectsFactory
                .getActivityForUser(userId)
                .setActivity(activityValue)
                .setActivityDate(start.plusDays(1).plusMinutes(1)));
        UserActivity middleActivityTwo = repository.save(DBObjectsFactory
                .getActivityForUser(userId)
                .setActivity(activityValue)
                .setActivityDate(start.plusDays(1).plusMinutes(12)));
        UserActivity middleActivityThree = repository.save(DBObjectsFactory
                .getActivityForUser(userId)
                .setActivity(activityValue)
                .setActivityDate(start.plusDays(1).plusMinutes(3)));
        UserActivity nowActivity = repository.save(DBObjectsFactory
                .getActivityForUser(userId));

        repository.saveAll(Arrays.asList(oldActivity, middleActivityOne, middleActivityTwo, middleActivityThree, nowActivity));

        List<UserActivity> activities = repository.findAllByUidAndActivityDateBetweenOrderByActivityDate(userId, start, end);

        assertEquals(3, activities.size());

        UserActivity activity = activities.get(0);
        assertEquals(middleActivityOne.getActivity(), activity.getActivity());
        assertEquals(middleActivityOne.getActivityDate(), activity.getActivityDate());

        activity = activities.get(1);
        assertEquals(middleActivityThree.getActivity(), activity.getActivity());
        assertEquals(middleActivityThree.getActivityDate(), activity.getActivityDate());

        activity = activities.get(2);
        assertEquals(middleActivityTwo.getActivity(), activity.getActivity());
        assertEquals(middleActivityTwo.getActivityDate(), activity.getActivityDate());
    }

    @Test
    public void testNewActivitySaveAndFindByUid() {
        UserActivity newActivity = repository.save(DBObjectsFactory.getActivityForUser(userId));

        repository.save(newActivity);

        List<UserActivity> activities = repository.findByUid(userId);

        assertEquals(1, activities.size());

        UserActivity savedActivity = activities.get(0);

        assertEquals(newActivity.getActivityDate(), savedActivity.getActivityDate());
        assertEquals(newActivity.getActivity(), savedActivity.getActivity());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testNewActivitySaveWithNoUserExists() {
        UserActivity newActivity = repository.save(DBObjectsFactory.getActivityForUser(UUID.randomUUID()));

        repository.saveAndFlush(newActivity);
    }
}
