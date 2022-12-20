package com.citalacki_dnevnik.server.model.bookReviewLike;

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
@Table(name = "book_review_like")
public class BookReviewLike extends AbstractStatusEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "book_review_id")
    private BookReview bookReview;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
