package org.example.basic.repository;

import static org.example.basic.util.Constants.COUNTRY;
import static org.example.basic.util.Constants.MONEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.example.basic.IntegrationTest;
import org.example.basic.dto.CountryStatDto;
import org.example.basic.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * IT Case for {@link UserRepository}.
 *
 * @author Igor_Orlov
 */
@IntegrationTest
@RunWith(SpringRunner.class)
public class UserRepositoryITCase {

    private final Long HUGE_MONEY = 1000L;

    @Autowired
    private UserRepository repository;

    @Test
    public void testSavingNewUser() {
        User newUser = getNewUser();

        UUID uid = repository.saveAndFlush(newUser).getUid();

        Optional<User> savedUser = repository.findById(uid);

        assertTrue(savedUser.isPresent());
        assertEquals(newUser.getCountry(), savedUser.get().getCountry());
        assertEquals(newUser.getMoney(), savedUser.get().getMoney());
    }

    @Test
    public void testUpdatingExistsUserMoney() {
        User existingUser = repository.saveAndFlush(getNewUser());
        UUID existingUserId = existingUser.getUid();

        User newUser = getNewUser()
                .setMoney(HUGE_MONEY)
                .setUid(existingUserId);

        repository.saveAndFlush(newUser);

        Optional<User> updatedUser = repository.findById(existingUserId);

        assertTrue(updatedUser.isPresent());
        assertEquals(existingUser.getCountry(), updatedUser.get().getCountry());
        assertEquals(HUGE_MONEY, updatedUser.get().getMoney());
    }

    @Test
    public void testFindTop1ByCountry() {
        repository.saveAll(Arrays.asList(
                getNewUser(),
                getNewUser().setMoney(HUGE_MONEY)
        ));

        Pageable page = PageRequest.of(0, 1, Sort.by(User.$.money).descending());

        List<User> users = repository.findAllByCountry(COUNTRY, page);

        assertEquals(1, users.size());
        assertEquals(HUGE_MONEY, users.get(0).getMoney());
        assertEquals(COUNTRY, users.get(0).getCountry());
    }

    private User getNewUser() {
        return new User()
                .setCountry(COUNTRY)
                .setMoney(MONEY)
                .setActivities(Collections.emptyList());
    }

}
