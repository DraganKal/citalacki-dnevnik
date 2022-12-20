package com.citalacki_dnevnik.server.controller.bookReviewComment;

import com.citalacki_dnevnik.server.model.dto.bookReviewComment.BookReviewCommentDTO;
import com.citalacki_dnevnik.server.model.dto.bookReviewCommentLike.BookReviewCommentLikeDTO;
import com.citalacki_dnevnik.server.service.bookReviewComment.BookReviewCommentService;
import com.citalacki_dnevnik.server.service.bookReviewCommentLike.BookReviewCommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-review-comment")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookReviewCommentController {

    private final BookReviewCommentService bookReviewCommentService;
    private final BookReviewCommentLikeService bookReviewCommentLikeService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookReviewCommentDTO> create(@PathVariable Long id) {
        return new ResponseEntity<>(bookReviewCommentService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookReviewCommentDTO> create(@RequestBody BookReviewCommentDTO bookReviewCommentDTO) {
        return new ResponseEntity<>(bookReviewCommentService.create(bookReviewCommentDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{bookReviewCommentId}/like-user-id/{userId}")
    public ResponseEntity<BookReviewCommentLikeDTO> unlike(@PathVariable Long bookReviewCommentId, @PathVariable Long userId) {
        return new ResponseEntity<>(bookReviewCommentLikeService.delete(bookReviewCommentId, userId), HttpStatus.OK);
    }

}
