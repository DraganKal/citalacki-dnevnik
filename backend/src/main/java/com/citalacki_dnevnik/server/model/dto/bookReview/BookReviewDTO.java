package com.citalacki_dnevnik.server.model.dto.bookReview;

import com.citalacki_dnevnik.server.model.user.UserGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewDTO {

    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    private int bookRating;

    private Long bookId;

    private String bookName;

    private String bookImageUrl;

    private Long userId;

    private String userUsername;

    private LocalDate createdDate;

    private int likes;

    private List<Long> likesUserIds = new ArrayList<>();

}
