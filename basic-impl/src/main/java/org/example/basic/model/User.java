package org.example.basic.model;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User.
 *
 * @author Igor_Orlov
 */
@Entity
@Data
@Accessors(chain = true)
@Table(name = "user_info")
@FieldNameConstants
@SequenceGenerator(name = User.ID_SEQ, sequenceName = User.ID_SEQ, allocationSize = 1)
public class User {

    public static final String ID_SEQ = "user_info_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_SEQ)
    Long id;

    private UUID uid;

    @CreationTimestamp
    @Column(updatable = false)
    private Date creationDate;

    private String country;

    private Long money;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<UserActivity> activities;

}
