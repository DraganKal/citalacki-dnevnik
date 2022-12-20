package com.citalacki_dnevnik.server.model.dto.bookReviewCommentLike;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewCommentLikeDTO {

    private Long id;

    private Long bookReviewCommentId;

    private Long userId;

}
