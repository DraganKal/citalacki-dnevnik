package com.citalacki_dnevnik.server.model.auth;


import com.citalacki_dnevnik.server.model.dto.user.AuthUserDTO;
import com.citalacki_dnevnik.server.model.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 DTO used for logging in/getting da token
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;

    private AuthUserDTO user;

}
