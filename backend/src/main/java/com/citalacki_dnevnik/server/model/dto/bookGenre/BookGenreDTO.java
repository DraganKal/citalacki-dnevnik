package com.citalacki_dnevnik.server.model.dto.bookGenre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookGenreDTO {

    private Long id;

    private String name;

    private int numberOfBooks;

}
