package org.example.basic.repository;

import org.example.basic.model.UserActivity;

import org.springframework.data.jpa.repository.JpaRepository;
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
public interface ActivityRepository extends JpaRepository<UserActivity, Long> {

    List<UserActivity> findAllByUidAndActivityDateBetween(UUID uid, Date start, Date end);

}
