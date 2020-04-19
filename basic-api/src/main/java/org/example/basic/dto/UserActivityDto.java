package org.example.basic.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.UUID;

/**
 * UserActivityFullDto.
 *
 * @author Igor_Orlov
 */
@Data
@Accessors(chain = true)
public class UserActivityDto {

    private UUID uid;
    private Long activity;
    private Date activityDate;

}
