package com.citalacki_dnevnik.server.controller.bookReviewCommentLike;

import com.citalacki_dnevnik.server.model.dto.bookReviewCommentLike.BookReviewCommentLikeDTO;
import com.citalacki_dnevnik.server.service.bookReviewCommentLike.BookReviewCommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-review-comment-like")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookReviewCommentLikeController {

    private final BookReviewCommentLikeService bookReviewCommentLikeService;

    @PostMapping
    public ResponseEntity<BookReviewCommentLikeDTO> create(@RequestBody BookReviewCommentLikeDTO bookReviewCommentLikeDTO) {
        return new ResponseEntity<>(bookReviewCommentLikeService.create(bookReviewCommentLikeDTO), HttpStatus.OK);
    }
}
