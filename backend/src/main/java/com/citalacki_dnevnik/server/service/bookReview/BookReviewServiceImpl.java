package com.citalacki_dnevnik.server.service.bookReview;

import com.citalacki_dnevnik.server.config.error.BadRequestException;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.book.Book;
import com.citalacki_dnevnik.server.model.bookReview.BookReview;
import com.citalacki_dnevnik.server.model.bookReviewLike.BookReviewLike;
import com.citalacki_dnevnik.server.model.dto.bookReview.BookReviewDTO;
import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.repository.book.BookRepository;
import com.citalacki_dnevnik.server.repository.bookReview.BookReviewRepository;
import com.citalacki_dnevnik.server.repository.bookReviewLike.BookReviewLikeRepository;
import com.citalacki_dnevnik.server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookReviewServiceImpl implements BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookReviewLikeRepository bookReviewLikeRepository;


    @Override
    public Page<BookReviewDTO> findAllPageable(Pageable pageable) {
        return bookReviewRepository.findByEntityStatus(EntityStatus.REGULAR, pageable).map(this::convertToDTO);
    }

    @Override
    public Page<BookReviewDTO> getAllByBookIdPageable(Long bookId, Pageable pageable) {
        return bookReviewRepository.findAllByBookIdAndEntityStatus(bookId, EntityStatus.REGULAR, pageable).map(this::convertToDTO);
    }

    @Override
    public BookReviewDTO getBookReview(Long id) {
        BookReview bookreview = bookReviewRepository.findByIdAndEntityStatus(id, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(BOOK_REVIEW_NOT_FOUND));
        return convertToDTO(bookreview);
    }

    @Override
    @Transactional
    public BookReviewDTO create(BookReviewDTO bookReviewDTO) {
        BookReview newBookReview = new BookReview();
        newBookReview = convertFromDTO(bookReviewDTO);
        newBookReview.setEntityStatus(EntityStatus.REGULAR);

        BookReview saved = bookReviewRepository.save(newBookReview);

        return convertToDTO(saved);
    }

    @Override
    public BookReviewDTO convertToDTO(BookReview bookReview) {
        BookReviewDTO dto = new BookReviewDTO();
        dto.setId(bookReview.getId());
        dto.setTitle(bookReview.getTitle());
        dto.setText(bookReview.getText());
        dto.setBookRating(bookReview.getBookRating());

        if(bookReview.getBook() != null) {
            dto.setBookId(bookReview.getBook().getId());
            dto.setBookName(bookReview.getBook().getName());
            dto.setBookImageUrl(bookReview.getBook().getImageUrl());
        }

        if(bookReview.getUser() != null) {
            dto.setUserId(bookReview.getUser().getId());
            dto.setUserUsername(bookReview.getUser().getUsername());
        }

        dto.setCreatedDate(LocalDate.ofInstant(bookReview.getCreatedDate(), ZoneOffset.UTC));
        List<BookReviewLike> likes = bookReviewLikeRepository.findAllByBookReviewId(bookReview.getId());
        dto.setLikes(likes.size());
        List<Long> likesUserIds = new ArrayList<>();
        for(BookReviewLike like : likes) {
            likesUserIds.add(like.getUser().getId());
        }
        dto.setLikesUserIds(likesUserIds);
        return dto;
    }

    @Override
    public BookReview convertFromDTO(BookReviewDTO bookReviewDTO) {
        BookReview bookReview = new BookReview();
        bookReview.setId(bookReviewDTO.getId());
        bookReview.setTitle(bookReviewDTO.getTitle());
        bookReview.setText(bookReviewDTO.getText());
        bookReview.setBookRating(bookReviewDTO.getBookRating());

        if(bookReviewDTO.getBookId() != null) {
            Book book = bookRepository.findByIdAndEntityStatus(bookReviewDTO.getBookId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(BOOK_NOT_FOUND));
            bookReview.setBook(book);
        }

        if(bookReviewDTO.getUserId() != null) {
            User user = userRepository.findByIdAndEntityStatus(bookReviewDTO.getUserId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
            bookReview.setUser(user);
        }

        return bookReview;
    }
}
