package com.citalacki_dnevnik.server.model.dto.user;

import com.citalacki_dnevnik.server.model.user.UserGroup;
import com.citalacki_dnevnik.server.model.user.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotEmpty(message = "Ime je obavezno")
    private String firstName;

    @NotEmpty(message = "Prezime je obavezno")
    private String lastName;

    @NotEmpty(message = "Korisnicko ime je obavezno")
    private String username;

    @NotEmpty(message = "Email je obavezan")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 6, message = "Lozinka mora imati najmanje 6 karaktera")
    private String password;

    private String repeatPassword;

    private LocalDate birthDate;

    private String city;

    private String phone;

}
