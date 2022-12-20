package com.citalacki_dnevnik.server.model.user;

import com.citalacki_dnevnik.server.model.user.enums.UserType;
import com.citalacki_dnevnik.server.model.AbstractStatusEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends AbstractStatusEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @Column(name = "password_hash", length = 200)
    private String password;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(name = "user_type", nullable = false)
    private UserType userType;

    // contact related data
    @Column(length = 100)
    private String city;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(length = 50)
    private String phone;


    @Column(name = "image_url")
    private String imageUrl;

//    @Column(nullable = false)
//    private boolean active;

    @ManyToMany
    @JoinTable(name = "user_user_group",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_group_id")})
    private List<UserGroup> userGroups = new ArrayList<>();
}
