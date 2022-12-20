package com.citalacki_dnevnik.server.repository.bookReviewComment;

import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.bookReviewComment.BookReviewComment;
import com.citalacki_dnevnik.server.repository.AbstractStatusEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookReviewCommentRepository extends AbstractStatusEntityRepository<BookReviewComment, Long> {

    Page<BookReviewComment> findAllByBookReviewIdAndEntityStatus(Long bookReviewId, EntityStatus entityStatus, Pageable pageable);

}
