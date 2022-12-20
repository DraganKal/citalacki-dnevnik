package com.citalacki_dnevnik.server.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationDTO {

    private Long id;

    private String text;

    private String url;

    private Long userId;

    private LocalDateTime time;

}
