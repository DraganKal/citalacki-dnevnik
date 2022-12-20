package com.citalacki_dnevnik.server.model.user;

import com.citalacki_dnevnik.server.model.AbstractStatusEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_notification")
public class UserNotification extends AbstractStatusEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "text", length = 2000, nullable = false)
    private String text;

    @Column(name = "url", length = 50, nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
