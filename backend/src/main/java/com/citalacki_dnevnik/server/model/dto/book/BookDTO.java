package com.citalacki_dnevnik.server.model.dto.book;

import com.citalacki_dnevnik.server.model.dto.bookGenre.BookGenreDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String author;

    private Integer publishedYear;

    private String description;

    private String imageUrl;

    private List<String> genresNames;

    private List<Long> genresIds;

    private List<BookGenreDTO> genresDTOS;

    private int reviewNumbers;

    private double rate;

    private int rating;

}
