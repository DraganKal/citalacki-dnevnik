package com.citalacki_dnevnik.server.service.bookReviewCommentLike;

import com.citalacki_dnevnik.server.model.bookReviewCommentLike.BookReviewCommentLike;
import com.citalacki_dnevnik.server.model.dto.bookReviewCommentLike.BookReviewCommentLikeDTO;

public interface BookReviewCommentLikeService {

    BookReviewCommentLikeDTO create(BookReviewCommentLikeDTO bookReviewCommentLikeDTO);

    BookReviewCommentLikeDTO delete(Long bookReviewCommentId, Long userId);

    BookReviewCommentLikeDTO convertToDTO(BookReviewCommentLike bookReviewCommentLike);

    BookReviewCommentLike convertFromDTO(BookReviewCommentLikeDTO bookReviewCommentLikeDTO);
}
