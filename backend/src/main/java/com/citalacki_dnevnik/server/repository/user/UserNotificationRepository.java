package com.citalacki_dnevnik.server.repository.user;

import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.book.Book;
import com.citalacki_dnevnik.server.model.user.UserNotification;
import com.citalacki_dnevnik.server.repository.AbstractStatusEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserNotificationRepository extends AbstractStatusEntityRepository<UserNotification, Long> {


    List<UserNotification> findAllByUserIdAndEntityStatusOrderByCreatedDateDesc(Long userId, EntityStatus entityStatus);

    Page<UserNotification> findAllByUserIdAndEntityStatus(Long userId, EntityStatus entityStatus, Pageable pageable);

}
