package com.citalacki_dnevnik.server.event.listener;

import com.citalacki_dnevnik.server.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserMailEvent {

    private User user;

    private String token;

}
