package com.citalacki_dnevnik.server.model.dto.user;

import com.citalacki_dnevnik.server.model.user.UserGroup;
import com.citalacki_dnevnik.server.model.user.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private UserType userType;

    // contact related data
    private String city;

    private LocalDate birthDate;

    private String phone;

    private String imageUrl;

    private List<UserGroup> userGroups = new ArrayList<>();

}

//    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
//    private String password;
