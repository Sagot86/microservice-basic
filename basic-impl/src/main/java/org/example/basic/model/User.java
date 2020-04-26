package org.example.basic.model;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "basic_user_info")
@FieldNameConstants
public class User {

    @Id
    @GeneratedValue
    @Column(name = "uid")
    private UUID uid;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @Column(updatable = false)
    private String country;

    private Long money;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "uid")
    private List<UserActivity> activities;

}
