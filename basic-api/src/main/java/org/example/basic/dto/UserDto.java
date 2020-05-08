package org.example.basic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private UUID uuid;
    private String country;
    private Long money;

}
