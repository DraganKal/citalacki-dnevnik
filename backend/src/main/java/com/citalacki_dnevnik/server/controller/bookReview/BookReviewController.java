package com.citalacki_dnevnik.server.controller.bookReview;

import com.citalacki_dnevnik.server.model.dto.bookReview.BookReviewDTO;
import com.citalacki_dnevnik.server.model.dto.bookReviewComment.BookReviewCommentDTO;
import com.citalacki_dnevnik.server.model.dto.bookReviewLike.BookReviewLikeDTO;
import com.citalacki_dnevnik.server.service.bookReview.BookReviewService;
import com.citalacki_dnevnik.server.service.bookReviewComment.BookReviewCommentService;
import com.citalacki_dnevnik.server.service.bookReviewLike.BookReviewLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-review")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookReviewController {

    private final BookReviewService bookReviewService;
    private final BookReviewCommentService bookReviewCommentService;
    private final BookReviewLikeService bookReviewLikeService;

    @PostMapping
    public ResponseEntity<BookReviewDTO> create(@RequestBody BookReviewDTO bookReviewDTO) {
        return new ResponseEntity<>(bookReviewService.create(bookReviewDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<BookReviewDTO>> findAllPageable(Pageable pageable) {
        return new ResponseEntity<>(bookReviewService.findAllPageable(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookReviewDTO> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(bookReviewService.getBookReview(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/comments")
    public ResponseEntity<Page<BookReviewCommentDTO>> getAllBookReviewCommentsByBookReviewIdPageable(@PathVariable() Long id,
                                                                                        Pageable pageable) {
        return new ResponseEntity<>(bookReviewCommentService.getAllByBookReviewIdPageable(id, pageable), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{bookReviewId}/like-user-id/{userId}")
    public ResponseEntity<BookReviewLikeDTO> unlike(@PathVariable Long bookReviewId, @PathVariable Long userId) {
        return new ResponseEntity<>(bookReviewLikeService.delete(bookReviewId, userId), HttpStatus.OK);
    }

}
