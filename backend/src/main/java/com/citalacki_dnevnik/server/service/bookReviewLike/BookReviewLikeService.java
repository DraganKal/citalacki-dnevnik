package com.citalacki_dnevnik.server.service.bookReviewLike;

import com.citalacki_dnevnik.server.model.bookReviewLike.BookReviewLike;
import com.citalacki_dnevnik.server.model.dto.bookReviewLike.BookReviewLikeDTO;

public interface BookReviewLikeService {

    BookReviewLikeDTO create(BookReviewLikeDTO bookReviewLikeDTO);

    BookReviewLikeDTO delete(Long bookReviewId, Long userId);

    BookReviewLikeDTO convertToDTO(BookReviewLike bookReviewLike);

    BookReviewLike convertFromDTO(BookReviewLikeDTO bookReviewLikeDTO);

}
