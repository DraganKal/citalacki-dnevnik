package com.citalacki_dnevnik.server.service.bookReview;

import com.citalacki_dnevnik.server.model.bookReview.BookReview;
import com.citalacki_dnevnik.server.model.dto.bookReview.BookReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookReviewService {

    Page<BookReviewDTO> findAllPageable(Pageable pageable);

    Page<BookReviewDTO> getAllByBookIdPageable(Long bookId, Pageable pageable);

    BookReviewDTO getBookReview(Long id);

    BookReviewDTO create(BookReviewDTO bookReviewDTO);

    BookReviewDTO convertToDTO(BookReview bookReview);

    BookReview convertFromDTO(BookReviewDTO bookReviewDTO);

}
