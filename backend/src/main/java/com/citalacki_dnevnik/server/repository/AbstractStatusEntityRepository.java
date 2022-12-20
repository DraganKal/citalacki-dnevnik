package com.citalacki_dnevnik.server.repository;

import com.citalacki_dnevnik.server.model.AbstractStatusEntity;
import com.citalacki_dnevnik.server.model.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 Abstract repository used by each repository meant for objects with status
 */
@NoRepositoryBean
public interface AbstractStatusEntityRepository<T extends AbstractStatusEntity, ID> extends JpaRepository<T, ID> {

    Page<T> findByEntityStatus(EntityStatus entityStatus, Pageable pageable);

    List<T> findByEntityStatus(EntityStatus entityStatus);

    Optional<T> findByIdAndEntityStatus(ID id, EntityStatus entityStatus);
}
