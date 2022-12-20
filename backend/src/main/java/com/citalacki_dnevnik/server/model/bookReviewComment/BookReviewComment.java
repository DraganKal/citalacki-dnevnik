package com.citalacki_dnevnik.server.model.bookReviewComment;

import com.citalacki_dnevnik.server.model.AbstractStatusEntity;
import com.citalacki_dnevnik.server.model.bookReview.BookReview;
import com.citalacki_dnevnik.server.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_review_comment")
public class BookReviewComment extends AbstractStatusEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "text",length = 10000, nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "book_review_id")
    private BookReview bookReview;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
