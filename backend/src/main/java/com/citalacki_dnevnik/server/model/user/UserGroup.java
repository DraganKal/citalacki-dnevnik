package com.citalacki_dnevnik.server.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.citalacki_dnevnik.server.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 User groups. they are used for grouping permissions
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user_group")
public class UserGroup extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(length = 25, unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = true)
    private String description;

    @ManyToMany
    @JoinTable(name = "user_group_user_permission",
            joinColumns = { @JoinColumn(name = "user_group_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_permission_id") })
    private List<UserPermission> userPermissions = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy="userGroups", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
}
