package com.citalacki_dnevnik.server.service.bookReviewComment;

import com.citalacki_dnevnik.server.model.bookReviewComment.BookReviewComment;
import com.citalacki_dnevnik.server.model.dto.bookReviewComment.BookReviewCommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookReviewCommentService {

    Page<BookReviewCommentDTO> getAllByBookReviewIdPageable(Long bookReviewId, Pageable pageable);

    BookReviewCommentDTO getById(Long id);

    BookReviewCommentDTO create(BookReviewCommentDTO bookReviewCommentDTO);

    BookReviewCommentDTO convertToDTO(BookReviewComment bookReviewComment);

    BookReviewComment convertFromDTO(BookReviewCommentDTO bookReviewCommentDTO);

}
