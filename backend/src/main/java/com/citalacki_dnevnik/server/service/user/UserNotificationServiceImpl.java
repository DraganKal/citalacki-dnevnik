package com.citalacki_dnevnik.server.service.user;

import com.citalacki_dnevnik.server.config.error.BadRequestException;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.dto.book.BookDTO;
import com.citalacki_dnevnik.server.model.dto.user.UserNotificationDTO;
import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.model.user.UserNotification;
import com.citalacki_dnevnik.server.repository.user.UserNotificationRepository;
import com.citalacki_dnevnik.server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserNotificationServiceImpl implements UserNotificationService {

    private final UserNotificationRepository userNotificationRepository;
    private final UserRepository userRepository;

    @Override
    public UserNotificationDTO convertToDTO(UserNotification userNotification) {
        UserNotificationDTO userNotificationDTO = new UserNotificationDTO();

        userNotificationDTO.setId(userNotification.getId());
        userNotificationDTO.setText(userNotification.getText());
        userNotificationDTO.setUrl(userNotification.getUrl());
        userNotificationDTO.setUserId(userNotification.getUser().getId());

        if(userNotification.getUser() != null) {
            userNotificationDTO.setUserId(userNotification.getUser().getId());
        }

        userNotificationDTO.setTime(LocalDateTime.ofInstant(userNotification.getCreatedDate(), ZoneId.of("Europe/Belgrade")));


        return userNotificationDTO;
    }

    @Override
    public UserNotification convertFromDTO(UserNotificationDTO userNotificationDTO) {
        UserNotification userNotification = new UserNotification();

        userNotification.setId(userNotificationDTO.getId());
        userNotification.setText(userNotificationDTO.getText());
        userNotification.setUrl(userNotificationDTO.getUrl());

        if(userNotificationDTO.getUserId() != null) {
            User user = userRepository.findByIdAndEntityStatus(userNotificationDTO.getUserId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
            userNotification.setUser(user);
        }
        return userNotification;
    }

    @Override
    public List<UserNotificationDTO> getAllPresentNotificationsByUserId(Long userId) {
        return userNotificationRepository.findAllByUserIdAndEntityStatusOrderByCreatedDateDesc(userId, EntityStatus.REGULAR)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserNotificationDTO deleteById(Long userId, Long userNotificationId) {
        User user = userRepository.findByIdAndEntityStatus(userId, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        UserNotification notification = userNotificationRepository.findByIdAndEntityStatus(userNotificationId, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(USER_NOTIFICATION_NOT_FOUND));
        notification.setEntityStatus(EntityStatus.DELETED);
        UserNotification deleted = userNotificationRepository.save(notification);
        return convertToDTO(deleted);
    }

    @Override
    @Transactional
    public List<UserNotificationDTO> deleteAllByUserId(Long userId) {
        User user = userRepository.findByIdAndEntityStatus(userId, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        List<UserNotification> notifications = userNotificationRepository.findAllByUserIdAndEntityStatusOrderByCreatedDateDesc(userId, EntityStatus.REGULAR);
        List<UserNotification> forDelete = new ArrayList<>();
        for(UserNotification notification : notifications) {
            notification.setEntityStatus(EntityStatus.DELETED);
            forDelete.add(notification);
        }
        List<UserNotification> deleted = userNotificationRepository.saveAll(forDelete);
        return deleted
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserNotificationDTO> getAllReadedByUserIdPageable(Long userId, Pageable pageable) {
        return userNotificationRepository.findAllByUserIdAndEntityStatus(userId, EntityStatus.DELETED, pageable).map(this::convertToDTO);
    }

}
