package org.example.basic.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * Activity.
 *
 * @author Igor_Orlov
 */
@Entity
@Data
@Accessors(chain = true)
@Table(name = "user_activity")
@SequenceGenerator(name = UserActivity.ID_SEQ, sequenceName = UserActivity.ID_SEQ, allocationSize = 1)
public class UserActivity {

    public static final String ID_SEQ = "user_activity_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQ)
    private Long id;

    @Column(name = "user_id")
    private UUID userId;

    private Long activity;

    @CreationTimestamp
    @Column(updatable = false)
    private Date activityDate;

}
