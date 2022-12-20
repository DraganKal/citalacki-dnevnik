package com.citalacki_dnevnik.server.model.dto.user;

import lombok.Data;

@Data
public class PasswordDTO {

    private String token;

    private String newPassword;

}
