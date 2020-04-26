package org.example.basic.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * UserFullDto.
 *
 * @author Igor_Orlov
 */
@Data
@Accessors(chain = true)
public class UserDtoFull {

    private UUID id;
    private String country;
    private Long money;
    private Date creationDate;
    private List<UserActivityDto> activities;

}
