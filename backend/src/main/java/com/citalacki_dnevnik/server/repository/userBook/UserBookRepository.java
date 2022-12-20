package com.citalacki_dnevnik.server.repository.userBook;

import com.citalacki_dnevnik.server.model.book.Book;
import com.citalacki_dnevnik.server.model.userBook.UserBook;
import com.citalacki_dnevnik.server.model.userBook.enums.UserBookState;
import com.citalacki_dnevnik.server.repository.AbstractStatusEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserBookRepository extends AbstractStatusEntityRepository<UserBook, Long> {

    UserBook findByUserIdAndBookId(Long userId, Long bookId);
    List<UserBook> findAllByUserId(Long userId);

    Page<UserBook> findAllByUserIdAndState(Long userId, UserBookState state, Pageable pageable);

}
