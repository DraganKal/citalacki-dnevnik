package com.citalacki_dnevnik.server.repository.book;

import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.book.Book;
import com.citalacki_dnevnik.server.repository.AbstractStatusEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends AbstractStatusEntityRepository<Book, Long> {

    Book findByNameAndEntityStatus(String name, EntityStatus entityStatus);

    @Query(value = "SELECT book FROM Book book "
            + "WHERE book.entityStatus = com.citalacki_dnevnik.server.model.EntityStatus.REGULAR "
            + "AND CONCAT(book.name,book.author) LIKE %:filter% ")
    Page<Book> findAllByNameAndAuthor(@Param("filter") String filter, Pageable pageable);

    @Query(value = "SELECT book FROM Book book JOIN book.genres genre " +
            "ON genre.id = :bookGenreId " +
            "WHERE book.entityStatus = com.citalacki_dnevnik.server.model.EntityStatus.REGULAR")
    Page<Book> findAllByBookGenreId(@Param("bookGenreId") Long bookGenreId, Pageable pageable);

}
