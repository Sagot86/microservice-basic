package org.example.basic.mapper;

import org.example.basic.dto.UserActivityDto;
import org.example.basic.model.UserActivity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ActivityMapper.
 *
 * @author Igor_Orlov
 */
@Component
public class ActivityMapper {

    public UserActivityDto mapToActivityDto(UserActivity activity) {
        return new UserActivityDto()
                .setUid(activity.getUserId())
                .setActivity(activity.getActivity())
                .setActivityDate(activity.getActivityDate());
    }

    public List<UserActivityDto> mapToActivityDto(List<UserActivity> activities) {
        return activities.stream()
                .map(this::mapToActivityDto)
                .collect(Collectors.toList());
    }

}
