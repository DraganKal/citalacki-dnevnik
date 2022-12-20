package com.citalacki_dnevnik.server.repository.user;

import com.citalacki_dnevnik.server.repository.AbstractStatusEntityRepository;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.user.UserPasswordResetToken;

import java.util.Optional;

public interface UserPasswordResetTokenRepository extends AbstractStatusEntityRepository<UserPasswordResetToken,Long> {

    Optional<UserPasswordResetToken> findByToken(String token);

    Optional<UserPasswordResetToken> findTopByUserIdAndEntityStatusOrderByCreatedDateDesc(Long userId, EntityStatus entityStatus);

}
