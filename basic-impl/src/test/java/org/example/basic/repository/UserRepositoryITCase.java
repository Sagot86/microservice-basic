package org.example.basic.repository;

import static org.example.basic.util.Constants.COUNTRY;
import static org.example.basic.util.Constants.NOW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.example.basic.IntegrationTest;
import org.example.basic.dto.CountryStatDto;
import org.example.basic.model.User;
import org.example.basic.util.DBObjectsFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        User newUser = DBObjectsFactory.getNewUser();

        UUID uid = repository.saveAndFlush(newUser).getUid();

        Optional<User> savedUser = repository.findById(uid);

        assertTrue(savedUser.isPresent());
        assertEquals(newUser.getCountry(), savedUser.get().getCountry());
        assertEquals(newUser.getMoney(), savedUser.get().getMoney());
    }

    @Test
    public void testUpdatingExistsUserMoney() {
        User existingUser = repository.saveAndFlush(DBObjectsFactory.getNewUser());
        UUID existingUserId = existingUser.getUid();

        User newUser = DBObjectsFactory.getNewUser()
                .setMoney(HUGE_MONEY)
                .setUid(existingUserId);

        repository.save(newUser);

        Optional<User> updatedUser = repository.findById(existingUserId);

        assertTrue(updatedUser.isPresent());
        assertEquals(existingUser.getCountry(), updatedUser.get().getCountry());
        assertEquals(HUGE_MONEY, updatedUser.get().getMoney());
    }

    @Test
    public void testFindTop1ByCountry() {
        repository.saveAll(Arrays.asList(
                DBObjectsFactory.getNewUser(),
                DBObjectsFactory.getNewUser().setMoney(HUGE_MONEY)
        ));

        Pageable page = PageRequest.of(0, 1, Sort.by(User.$.money).descending());

        List<User> users = repository.findAllByCountry(COUNTRY, page);

        assertEquals(1, users.size());
        assertEquals(HUGE_MONEY, users.get(0).getMoney());
        assertEquals(COUNTRY, users.get(0).getCountry());
    }

    @Test
    public void testCountNewUsersForEachCounty() {
        String country = "Country";
        repository.save(DBObjectsFactory.getNewUser()
                .setCreationDate(NOW.minusDays(5)));
        repository.save(DBObjectsFactory.getNewUser()
                .setCreationDate(NOW.minusDays(1)));
        repository.save(DBObjectsFactory.getNewUser()
                .setCreationDate(NOW.minusDays(1)));

        repository.save(DBObjectsFactory.getNewUser()
                .setCreationDate(NOW.minusDays(1))
                .setCountry(country));
        repository.save(DBObjectsFactory.getNewUser()
                .setCreationDate(NOW.minusDays(1))
                .setCountry(country));

        List<CountryStatDto> countries = repository.countNewUsersForEachCounty(NOW.minusDays(2), NOW);

        for (CountryStatDto dto : countries) {
            assertEquals(Long.valueOf(2), dto.getUserCount());
        }

        List<String> countriesList = countries.stream()
                .map(CountryStatDto::getCountry)
                .collect(Collectors.toList());

        assertEquals(2, countriesList.size());
        assertTrue(countriesList.contains(COUNTRY));
        assertTrue(countriesList.contains(country));

    }


}
