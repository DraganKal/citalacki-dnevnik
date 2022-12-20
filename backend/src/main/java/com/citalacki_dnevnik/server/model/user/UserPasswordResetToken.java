package com.citalacki_dnevnik.server.model.user;


import com.citalacki_dnevnik.server.model.AbstractStatusEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity(name = "user_password_reset_token")
public class UserPasswordResetToken extends AbstractStatusEntity {

    private static final int EXPIRATION = 17280;

    private String token;

    @OneToOne(optional = false)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private boolean used;

}
