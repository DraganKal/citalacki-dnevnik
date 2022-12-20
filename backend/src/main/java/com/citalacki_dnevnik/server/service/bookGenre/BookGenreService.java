package com.citalacki_dnevnik.server.service.bookGenre;

import com.citalacki_dnevnik.server.model.bookGenre.BookGenre;
import com.citalacki_dnevnik.server.model.dto.book.BookDTO;
import com.citalacki_dnevnik.server.model.dto.bookGenre.BookGenreDTO;

import java.util.List;

public interface BookGenreService {

    BookGenreDTO convertToDTO(BookGenre bookGenre);

    BookGenre convertFromDTO(BookGenreDTO bookGenreDTO);

    BookGenreDTO createBookGenre(BookGenreDTO bookGenreDTO);

    List<BookGenreDTO> getAllBookGenres();

}
