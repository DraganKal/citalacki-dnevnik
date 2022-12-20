package com.citalacki_dnevnik.server.repository.bookReviewLike;

import com.citalacki_dnevnik.server.model.bookReviewLike.BookReviewLike;
import com.citalacki_dnevnik.server.repository.AbstractStatusEntityRepository;

import java.util.List;

public interface BookReviewLikeRepository extends AbstractStatusEntityRepository<BookReviewLike, Long> {

    List<BookReviewLike> findAllByBookReviewId(Long bookReviewId);

    BookReviewLike findByBookReviewIdAndUserId(Long bookReviewId, Long userId);

}
