package org.example.basic.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Activity.
 *
 * @author Igor_Orlov
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldNameConstants
@Accessors(chain = true)
@Table(name = "basic_user_activity")
@SequenceGenerator(name = UserActivity.ID_SEQ, sequenceName = UserActivity.ID_SEQ, allocationSize = 1)
public class UserActivity {

    public static final String ID_SEQ = "basic_user_activity_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQ)
    private Long id;

    @Column(name = "uid")
    private UUID uid;

    @Column(name = "activity_value")
    private Long activity;

    @Column(name = "activity_date", updatable = false)
    private LocalDateTime activityDate;

    @PrePersist
    public void prePersist() {
        if (activityDate == null) {
            activityDate = LocalDateTime.now();
        }
    }

}
