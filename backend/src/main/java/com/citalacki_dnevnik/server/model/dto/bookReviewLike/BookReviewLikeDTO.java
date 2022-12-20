package com.citalacki_dnevnik.server.model.dto.bookReviewLike;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewLikeDTO {

    private Long id;

    private Long bookReviewId;

    private Long userId;

}
