package com.citalacki_dnevnik.server.service.bookReviewLike;

import com.citalacki_dnevnik.server.config.error.BadRequestException;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.bookReview.BookReview;
import com.citalacki_dnevnik.server.model.bookReviewLike.BookReviewLike;
import com.citalacki_dnevnik.server.model.dto.bookReviewLike.BookReviewLikeDTO;
import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.model.user.UserNotification;
import com.citalacki_dnevnik.server.repository.bookReview.BookReviewRepository;
import com.citalacki_dnevnik.server.repository.bookReviewLike.BookReviewLikeRepository;
import com.citalacki_dnevnik.server.repository.user.UserNotificationRepository;
import com.citalacki_dnevnik.server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookReviewLikeServiceImpl implements BookReviewLikeService {

    private final BookReviewLikeRepository bookReviewLikeRepository;
    private final BookReviewRepository bookReviewRepository;
    private final UserRepository userRepository;
    private final UserNotificationRepository userNotificationRepository;

    @Override
    @Transactional
    public BookReviewLikeDTO create(BookReviewLikeDTO bookReviewLikeDTO) {
        BookReviewLike like =
                bookReviewLikeRepository.findByBookReviewIdAndUserId(bookReviewLikeDTO.getBookReviewId(), bookReviewLikeDTO.getUserId());
        if(like != null) {
            throw new BadRequestException(BOOK_REVIEW_LIKE_ALREADY_EXIST);
        }
        BookReviewLike newBookReviewLike;
        newBookReviewLike = convertFromDTO(bookReviewLikeDTO);
        newBookReviewLike.setEntityStatus(EntityStatus.REGULAR);

        BookReviewLike saved = bookReviewLikeRepository.save(newBookReviewLike);

        if(!saved.getUser().equals(saved.getBookReview().getUser())) {
            UserNotification notification = new UserNotification();
            notification.setUser(saved.getBookReview().getUser());
            notification.setText(
                    "korisnik '" + saved.getUser().getUsername() + "' je lajkovao vasu recenziju za knjigu '"
                            + saved.getBookReview().getBook().getName() + "'"
            );
            notification.setUrl("/recenzije/sve/" + saved.getBookReview().getId());
            notification.setEntityStatus(EntityStatus.REGULAR);
            userNotificationRepository.save(notification);
        }

        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public BookReviewLikeDTO delete(Long bookReviewId, Long userId) {
        BookReviewLike like =
                bookReviewLikeRepository.findByBookReviewIdAndUserId(bookReviewId, userId);
        if(like == null) {
            throw new BadRequestException(BOOK_REVIEW_LIKE_NOT_FOUND);
        }
        bookReviewLikeRepository.delete(like);

        return convertToDTO(like);
    }

    @Override
    public BookReviewLikeDTO convertToDTO(BookReviewLike bookReviewLike) {
        BookReviewLikeDTO dto = new BookReviewLikeDTO();
        dto.setId(bookReviewLike.getId());

        if(bookReviewLike.getBookReview() != null) {
            dto.setBookReviewId(bookReviewLike.getBookReview().getId());
        }

        if(bookReviewLike.getUser() != null) {
            dto.setUserId(bookReviewLike.getUser().getId());
        };

        return dto;
    }

    @Override
    public BookReviewLike convertFromDTO(BookReviewLikeDTO bookReviewLikeDTO) {
        BookReviewLike bookReviewLike = new BookReviewLike();
        bookReviewLike.setId(bookReviewLikeDTO.getId());

        if(bookReviewLikeDTO.getBookReviewId() != null) {
            BookReview bookReview = bookReviewRepository.findByIdAndEntityStatus(bookReviewLikeDTO.getBookReviewId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(BOOK_REVIEW_NOT_FOUND));
            bookReviewLike.setBookReview(bookReview);
        }

        if(bookReviewLikeDTO.getUserId() != null) {
            User user = userRepository.findByIdAndEntityStatus(bookReviewLikeDTO.getUserId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
            bookReviewLike.setUser(user);
        }

        return bookReviewLike;
    }
}
