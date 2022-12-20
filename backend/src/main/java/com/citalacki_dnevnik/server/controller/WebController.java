package com.citalacki_dnevnik.server.controller;

import com.citalacki_dnevnik.server.model.dto.user.UserNotificationDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebController {

    @MessageMapping("/hello")
    @SendTo("/topic/hi")
    public UserNotificationDTO greeting(UserNotificationDTO userNotificationDTO) throws Exception {
        UserNotificationDTO dto = new UserNotificationDTO();
        dto.setId(1l);
        dto.setText("Neki kao tekst");
        dto.setUserId(2l);
        return dto;
    }

}
