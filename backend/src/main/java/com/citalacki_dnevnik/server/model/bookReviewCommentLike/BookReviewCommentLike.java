package com.citalacki_dnevnik.server.model.bookReviewCommentLike;

import com.citalacki_dnevnik.server.model.AbstractStatusEntity;
import com.citalacki_dnevnik.server.model.bookReview.BookReview;
import com.citalacki_dnevnik.server.model.bookReviewComment.BookReviewComment;
import com.citalacki_dnevnik.server.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_review_comment_like")
public class BookReviewCommentLike extends AbstractStatusEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "book_review_comment_id")
    private BookReviewComment bookReviewComment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
