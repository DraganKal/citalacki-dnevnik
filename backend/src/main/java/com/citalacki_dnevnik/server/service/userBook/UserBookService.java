package com.citalacki_dnevnik.server.service.userBook;

import com.citalacki_dnevnik.server.model.dto.book.BookDTO;
import com.citalacki_dnevnik.server.model.dto.userBook.UserBookDTO;
import com.citalacki_dnevnik.server.model.userBook.UserBook;
import com.citalacki_dnevnik.server.model.userBook.enums.UserBookState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserBookService {

    List<UserBookDTO> getAllUserBooksByUserId(Long userId);

    Page<UserBookDTO> getAllByUserIdAndStatePageable(Long userId, UserBookState state, Pageable pageable);

    UserBookDTO create(UserBookDTO userBookDTO);

    UserBookDTO delete(Long userId, Long bookId);

    UserBookDTO convertToDTO(UserBook userBook);

    UserBook convertFromDTO(UserBookDTO userBookDTO);

}
