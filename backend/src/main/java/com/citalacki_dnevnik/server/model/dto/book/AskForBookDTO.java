package com.citalacki_dnevnik.server.model.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AskForBookDTO {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String author;

    private boolean done;

    private Long userId;

    private String user;

}
