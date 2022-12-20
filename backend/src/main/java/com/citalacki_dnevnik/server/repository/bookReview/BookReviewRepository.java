package com.citalacki_dnevnik.server.repository.bookReview;

import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.bookReview.BookReview;
import com.citalacki_dnevnik.server.repository.AbstractStatusEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookReviewRepository extends AbstractStatusEntityRepository<BookReview, Long> {

    Page<BookReview> findAllByBookIdAndEntityStatus(Long bookId, EntityStatus entityStatus, Pageable pageable);

    List<BookReview> findAllByBookIdAndEntityStatus(Long bookId, EntityStatus entityStatus);

}
