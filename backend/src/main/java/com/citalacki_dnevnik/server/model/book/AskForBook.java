package com.citalacki_dnevnik.server.model.book;

import com.citalacki_dnevnik.server.model.AbstractStatusEntity;
import com.citalacki_dnevnik.server.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ask_for_book")
public class AskForBook extends AbstractStatusEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "author", length = 200, nullable = false)
    private String author;

    @Column(name = "done")
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
