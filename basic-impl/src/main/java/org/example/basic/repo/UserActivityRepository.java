package org.example.basic.repo;

import org.example.basic.model.UserActivity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * ActivityRepository.
 *
 * @author Igor_Orlov
 */
@Repository
public interface UserActivityRepository extends PagingAndSortingRepository<UserActivity, Long> {

    List<UserActivity> findAllByUserIdAndActivityDateBetween(UUID userId, Date start, Date end);

}
