package com.citalacki_dnevnik.server.model.dto.bookReviewComment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewCommentDTO {

    private Long id;

    @NotEmpty
    private String text;

    private Long bookReviewId;

    private Long userId;

    private String userUsername;

    private LocalDateTime createdTime;

    private int likes;

    private List<Long> likesUserIds = new ArrayList<>();

}
