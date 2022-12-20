package com.citalacki_dnevnik.server.repository.book;

import com.citalacki_dnevnik.server.model.book.AskForBook;
import com.citalacki_dnevnik.server.repository.AbstractStatusEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AskForBookRepository extends AbstractStatusEntityRepository<AskForBook, Long> {

    Page<AskForBook> findAllByDone(boolean done, Pageable pageable);

}
