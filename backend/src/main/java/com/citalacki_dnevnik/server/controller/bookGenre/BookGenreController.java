package com.citalacki_dnevnik.server.controller.bookGenre;

import com.citalacki_dnevnik.server.model.dto.book.BookDTO;
import com.citalacki_dnevnik.server.model.dto.bookGenre.BookGenreDTO;
import com.citalacki_dnevnik.server.service.bookGenre.BookGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-genre")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookGenreController {

    private final BookGenreService bookGenreService;

    @PostMapping
    public ResponseEntity<BookGenreDTO> createBookGenre(@RequestBody BookGenreDTO bookGenreDTO) {
        return new ResponseEntity<>(bookGenreService.createBookGenre(bookGenreDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookGenreDTO>> getAllBookGenres() {
        return new ResponseEntity<>(bookGenreService.getAllBookGenres(), HttpStatus.OK);
    }



}
