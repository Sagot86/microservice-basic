package org.example.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CountryStatDto.
 *
 * @author Igor_Orlov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryStatDto {

    private String country;
    private Long userCount;

}
