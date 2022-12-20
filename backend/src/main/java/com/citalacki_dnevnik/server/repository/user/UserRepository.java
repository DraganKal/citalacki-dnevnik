package com.citalacki_dnevnik.server.repository.user;

import com.citalacki_dnevnik.server.repository.AbstractStatusEntityRepository;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.user.User;

import java.util.Optional;

public interface UserRepository extends AbstractStatusEntityRepository<User, Long> {

    Optional<User> findByUsernameAndEntityStatus(String username, EntityStatus entityStatus);

    User findOneByUsernameAndEntityStatus(String username, EntityStatus entityStatus);

    Optional<User> findByEmailAndEntityStatus(String email, EntityStatus entityStatus);

}
