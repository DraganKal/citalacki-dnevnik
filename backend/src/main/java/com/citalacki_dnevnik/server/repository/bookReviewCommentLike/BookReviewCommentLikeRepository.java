package com.citalacki_dnevnik.server.repository.bookReviewCommentLike;

import com.citalacki_dnevnik.server.model.bookReviewCommentLike.BookReviewCommentLike;
import com.citalacki_dnevnik.server.repository.AbstractStatusEntityRepository;

import java.util.List;

public interface BookReviewCommentLikeRepository extends AbstractStatusEntityRepository<BookReviewCommentLike, Long> {

    List<BookReviewCommentLike> findAllByBookReviewCommentId(Long bookReviewCommentId);

    BookReviewCommentLike findByBookReviewCommentIdAndUserId(Long bookReviewCommentId, Long userId);
}
