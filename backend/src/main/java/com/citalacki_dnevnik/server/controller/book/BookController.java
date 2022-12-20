package com.citalacki_dnevnik.server.controller.book;

import com.citalacki_dnevnik.server.model.dto.book.BookDTO;
import com.citalacki_dnevnik.server.model.dto.bookReview.BookReviewDTO;
import com.citalacki_dnevnik.server.service.book.BookService;
import com.citalacki_dnevnik.server.service.bookReview.BookReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    private final BookService bookService;
    private final BookReviewService bookReviewService;

    @GetMapping(value = "/all")
    public ResponseEntity<Page<BookDTO>> findAllPageable(
            @RequestParam(value = "filter", required = false) Optional<String> filter,
            @RequestParam(value = "bookGenreId", required = false) Optional<Long> bookGenreId,
            Pageable pageable) {
        return new ResponseEntity<>(bookService.findAllPageable(filter, bookGenreId, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/all/{id}")
    public ResponseEntity<BookDTO> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBook(id), HttpStatus.OK);
    }

    @GetMapping(value = "/all-non-pageable")
    public ResponseEntity<List<BookDTO>> getAllBooksNonPageable() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.OK);
    }

    @PostMapping("/create-by-request/{id}")
    public ResponseEntity<BookDTO> createBookByRequest(@PathVariable(name = "id") Long askForBookId, @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.createBookByRequest(askForBookId, bookDTO), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO,
                                              @PathVariable Long id) {
        return new ResponseEntity<>(bookService.updateBook(id, bookDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/all/{id}/reviews")
    public ResponseEntity<Page<BookReviewDTO>> getAllBookReviewsByBookIdPageable(@PathVariable() Long id,
                                                                                         Pageable pageable) {
        return new ResponseEntity<>(bookReviewService.getAllByBookIdPageable(id, pageable), HttpStatus.OK);
    }


}
