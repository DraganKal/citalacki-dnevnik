package com.citalacki_dnevnik.server.model.dto.user;

import lombok.Data;

@Data
public class PasswordUpdateDTO {

    private String currentPassword;

    private String newPassword;

    private String confirmPassword;

}
