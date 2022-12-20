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
public class AuthUserDTO {

    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private UserType userType;

    private String imageUrl;

    private List<UserGroup> userGroups = new ArrayList<>();

}
