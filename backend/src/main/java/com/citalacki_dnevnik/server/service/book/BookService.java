package com.citalacki_dnevnik.server.service.book;

import com.citalacki_dnevnik.server.model.book.Book;
import com.citalacki_dnevnik.server.model.dto.book.BookDTO;
import com.citalacki_dnevnik.server.model.dto.user.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDTO> getAllBooks();

    Page<BookDTO> findAllPageable(Optional<String> filter, Optional<Long> bookGenreId, Pageable pageable);

    BookDTO getBook(Long id);

    BookDTO createBook(BookDTO bookDTO);

    BookDTO createBookByRequest(Long askForBookId, BookDTO bookDTO);

    BookDTO updateBook(Long id, BookDTO bookDTO);

    BookDTO convertToDTO(Book book);

    Book convertFromDTO(BookDTO bookDTO);

}
