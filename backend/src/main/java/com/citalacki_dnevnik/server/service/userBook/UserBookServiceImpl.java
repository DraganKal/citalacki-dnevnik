package com.citalacki_dnevnik.server.service.userBook;

import com.citalacki_dnevnik.server.config.error.BadRequestException;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.book.Book;
import com.citalacki_dnevnik.server.model.dto.userBook.UserBookDTO;
import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.model.userBook.UserBook;
import com.citalacki_dnevnik.server.model.userBook.enums.UserBookState;
import com.citalacki_dnevnik.server.repository.book.BookRepository;
import com.citalacki_dnevnik.server.repository.user.UserRepository;
import com.citalacki_dnevnik.server.repository.userBook.UserBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.*;
import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.USER_NOT_FOUND;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserBookServiceImpl implements UserBookService {

    private final UserBookRepository userBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public List<UserBookDTO> getAllUserBooksByUserId(Long userId) {
        return userBookRepository.findAllByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserBookDTO> getAllByUserIdAndStatePageable(Long userId, UserBookState state, Pageable pageable) {
        return userBookRepository.findAllByUserIdAndState(userId, state, pageable).map(this::convertToDTO);
    }

    @Override
    @Transactional
    public UserBookDTO create(UserBookDTO userBookDTO) {
        UserBook userBook =
                userBookRepository.findByUserIdAndBookId(userBookDTO.getUserId(), userBookDTO.getBookId());
        if(userBook != null) {
            throw new BadRequestException(USER_BOOK_ALREADY_EXIST);
        }
        UserBook newUserBook;
        newUserBook = convertFromDTO(userBookDTO);
        newUserBook.setEntityStatus(EntityStatus.REGULAR);
        newUserBook.setState(UserBookState.TO_READ);

        UserBook saved = userBookRepository.save(newUserBook);

        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public UserBookDTO delete(Long userId, Long bookId) {
        UserBook userBook =
                userBookRepository.findByUserIdAndBookId(userId, bookId);
        if(userBook == null) {
            throw new BadRequestException(USER_BOOK_NOT_FOUND);
        }
        userBookRepository.delete(userBook);

        return convertToDTO(userBook);
    }

    @Override
    public UserBookDTO convertToDTO(UserBook userBook) {
        UserBookDTO dto = new UserBookDTO();
        dto.setId(userBook.getId());
        dto.setState(userBook.getState());

        if(userBook.getUser() != null) {
            dto.setUserId(userBook.getUser().getId());
        }

        if(userBook.getBook() != null) {
            dto.setBookId(userBook.getBook().getId());
        };

        return dto;
    }

    @Override
    public UserBook convertFromDTO(UserBookDTO userBookDTO) {
        UserBook userBook = new UserBook();
        userBook.setId(userBookDTO.getId());
        userBook.setState(userBookDTO.getState());

        if(userBookDTO.getUserId() != null) {
            User user = userRepository.findByIdAndEntityStatus(userBookDTO.getUserId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
            userBook.setUser(user);
        }

        if(userBookDTO.getBookId() != null) {
            Book book = bookRepository.findByIdAndEntityStatus(userBookDTO.getBookId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(BOOK_NOT_FOUND));
            userBook.setBook(book);
        }

        return userBook;
    }

}
