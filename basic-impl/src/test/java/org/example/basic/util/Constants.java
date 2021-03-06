package org.example.basic.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * Constants for tests.
 *
 * @author Igor_Orlov
 */
@Component
public class Constants {

    public static final UUID USER_ID = UUID.randomUUID();
    public static final String COUNTRY = "Zimbabwe";
    public static final Long MONEY = 146L;
    public static final LocalDateTime NOW = LocalDateTime.now();
    public static final Long ACTIVITY = 100500L;

}
