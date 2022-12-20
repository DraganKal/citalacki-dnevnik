package com.citalacki_dnevnik.server.controller.bookReviewLike;

import com.citalacki_dnevnik.server.model.dto.bookReviewLike.BookReviewLikeDTO;
import com.citalacki_dnevnik.server.service.bookReviewLike.BookReviewLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-review-like")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookReviewLikeController {

    private final BookReviewLikeService bookReviewLikeService;

    @PostMapping
    public ResponseEntity<BookReviewLikeDTO> create(@RequestBody BookReviewLikeDTO bookReviewLikeDTO) {
        return new ResponseEntity<>(bookReviewLikeService.create(bookReviewLikeDTO), HttpStatus.OK);
    }

}
