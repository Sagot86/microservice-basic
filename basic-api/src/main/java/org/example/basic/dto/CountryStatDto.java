package org.example.basic.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * CountryStatDto.
 *
 * @author Igor_Orlov
 */
@Data
@RequiredArgsConstructor
public class CountryStatDto {

    private final String country;
    private final Long userCount;

}
