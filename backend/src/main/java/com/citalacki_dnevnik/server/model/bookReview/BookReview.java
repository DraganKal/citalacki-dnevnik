package com.citalacki_dnevnik.server.model.bookReview;

import com.citalacki_dnevnik.server.model.AbstractStatusEntity;
import com.citalacki_dnevnik.server.model.book.Book;
import com.citalacki_dnevnik.server.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_review")
public class BookReview extends AbstractStatusEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "title", length = 1000, nullable = false)
    private String title;

    @Column(name = "text",length = 10000, nullable = false)
    private String text;

    @Column(name = "book_rating")
    private int bookRating;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
