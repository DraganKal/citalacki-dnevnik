package com.citalacki_dnevnik.server.repository.user;

import com.citalacki_dnevnik.server.model.user.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    @Query("SELECT p FROM UserPermission p " +
            "JOIN p.userGroup g " +
            "JOIN g.users u ON u.id = :userId")
    List<UserPermission> findPermissionsByUserId(@Param("userId")Long userId);
}
