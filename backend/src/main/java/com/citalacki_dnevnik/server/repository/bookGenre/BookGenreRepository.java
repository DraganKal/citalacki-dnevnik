package com.citalacki_dnevnik.server.repository.bookGenre;

import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.bookGenre.BookGenre;
import com.citalacki_dnevnik.server.repository.AbstractStatusEntityRepository;

public interface BookGenreRepository extends AbstractStatusEntityRepository<BookGenre, Long> {

    BookGenre findByNameAndEntityStatus(String name, EntityStatus entityStatus);

}
