package com.citalacki_dnevnik.server.service.bookGenre;

import com.citalacki_dnevnik.server.config.error.BadRequestException;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.bookGenre.BookGenre;
import com.citalacki_dnevnik.server.model.dto.bookGenre.BookGenreDTO;
import com.citalacki_dnevnik.server.repository.bookGenre.BookGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookGenreServiceImpl implements BookGenreService {

    private final BookGenreRepository bookGenreRepository;


    @Override
    public BookGenreDTO convertToDTO(BookGenre bookGenre) {
        BookGenreDTO bookGenreDTO = new BookGenreDTO();
        bookGenreDTO.setId(bookGenre.getId());
        bookGenreDTO.setName(bookGenre.getName());

        if(bookGenre.getBooks().isEmpty()) {
            bookGenreDTO.setNumberOfBooks(0);
        } else {
            bookGenreDTO.setNumberOfBooks(bookGenre.getBooks().size());
        }
        return bookGenreDTO;
    }

    @Override
    public BookGenre convertFromDTO(BookGenreDTO bookGenreDTO) {
        BookGenre bookGenre = new BookGenre();

        bookGenre.setId(bookGenreDTO.getId());
        bookGenre.setName(bookGenreDTO.getName());

        return bookGenre;
    }

    @Override
    @Transactional
    public BookGenreDTO createBookGenre(BookGenreDTO bookGenreDTO) {
        BookGenre bookGenre = bookGenreRepository.findByNameAndEntityStatus(bookGenreDTO.getName(), EntityStatus.REGULAR);
        if(bookGenre != null) {
            throw new BadRequestException(BOOK_GENRE_NAME_ALREADY_EXIST);
        }
        BookGenre newBookGenre = new BookGenre();
        newBookGenre = convertFromDTO(bookGenreDTO);
        newBookGenre.setEntityStatus(EntityStatus.REGULAR);

        BookGenre savedBookGenre = bookGenreRepository.save(newBookGenre);
        return convertToDTO(savedBookGenre);
    }

    @Override
    public List<BookGenreDTO> getAllBookGenres() {
        return bookGenreRepository.findByEntityStatus(EntityStatus.REGULAR)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


}
