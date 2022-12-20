package com.citalacki_dnevnik.server.service.book;

import com.citalacki_dnevnik.server.model.book.AskForBook;
import com.citalacki_dnevnik.server.model.dto.book.AskForBookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AskForBookService {

    Page<AskForBookDTO> findAllNotDonePageable(Pageable pageable);

    AskForBookDTO getOne(Long id);

    AskForBookDTO create(AskForBookDTO askForBookDTO);

    AskForBookDTO convertToDTO(AskForBook askForBook);

    AskForBook convertFromDTO(AskForBookDTO askForBookDTO);

}
