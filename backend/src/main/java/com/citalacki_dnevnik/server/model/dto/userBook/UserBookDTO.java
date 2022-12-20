package com.citalacki_dnevnik.server.model.dto.userBook;

import com.citalacki_dnevnik.server.model.dto.book.BookDTO;
import com.citalacki_dnevnik.server.model.userBook.enums.UserBookState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookDTO {

    private Long id;

    private Long userId;

    private Long bookId;

    private BookDTO bookDTO;

    private UserBookState state;

}
