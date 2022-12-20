package com.citalacki_dnevnik.server.service.user;

import com.citalacki_dnevnik.server.model.bookGenre.BookGenre;
import com.citalacki_dnevnik.server.model.dto.book.BookDTO;
import com.citalacki_dnevnik.server.model.dto.bookGenre.BookGenreDTO;
import com.citalacki_dnevnik.server.model.dto.user.UserNotificationDTO;
import com.citalacki_dnevnik.server.model.user.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserNotificationService {

    UserNotificationDTO convertToDTO(UserNotification userNotification);

    UserNotification convertFromDTO(UserNotificationDTO userNotificationDTO);

    List<UserNotificationDTO> getAllPresentNotificationsByUserId(Long userId);

    UserNotificationDTO deleteById(Long userId, Long userNotificationId);

    List<UserNotificationDTO> deleteAllByUserId(Long userId);

    Page<UserNotificationDTO> getAllReadedByUserIdPageable(Long userId, Pageable pageable);

}
