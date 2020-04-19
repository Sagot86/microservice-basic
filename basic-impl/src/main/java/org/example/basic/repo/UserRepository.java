package org.example.basic.repo;

import org.example.basic.dto.CountryStatDto;
import org.example.basic.model.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * UserRepository.
 *
 * @author Igor_Orlov
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findUserByUid(UUID uid);

    List<User> findAllByCountry(String country, Pageable pageable);

    @Query("SELECT new org.example.basic.dto.CountryStatDto(u.country, COUNT(u.uid)) "
            + "FROM User AS u WHERE u.creationDate BETWEEN :startDate AND :endDate GROUP BY u.country ORDER BY u.country ASC")
    List<CountryStatDto> countNewUsersByCounty(
            @Param("startDate") Date start,
            @Param("endDate") Date end);

}
