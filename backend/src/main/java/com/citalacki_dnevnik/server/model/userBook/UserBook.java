package com.citalacki_dnevnik.server.model.userBook;

import com.citalacki_dnevnik.server.model.AbstractStatusEntity;
import com.citalacki_dnevnik.server.model.book.Book;
import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.model.userBook.enums.UserBookState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_book")
public class UserBook extends AbstractStatusEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "state", nullable = false)
    private UserBookState state;

}
