package com.citalacki_dnevnik.server.repository.user;

import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.model.user.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    List<UserGroup> findAllByUsers(User user);

}
