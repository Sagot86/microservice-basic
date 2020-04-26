package org.example.basic.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * UserDto.
 *
 * @author Igor_Orlov
 */
@Data
@Accessors(chain = true)
public class UserDto {

    private UUID id;
    private String country;
    private Long money;

}
