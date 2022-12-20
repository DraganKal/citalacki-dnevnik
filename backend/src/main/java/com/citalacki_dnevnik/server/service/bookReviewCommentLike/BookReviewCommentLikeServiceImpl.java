package com.citalacki_dnevnik.server.service.bookReviewCommentLike;

import com.citalacki_dnevnik.server.config.error.BadRequestException;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.bookReviewComment.BookReviewComment;
import com.citalacki_dnevnik.server.model.bookReviewCommentLike.BookReviewCommentLike;
import com.citalacki_dnevnik.server.model.dto.bookReviewCommentLike.BookReviewCommentLikeDTO;
import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.model.user.UserNotification;
import com.citalacki_dnevnik.server.repository.bookReviewComment.BookReviewCommentRepository;
import com.citalacki_dnevnik.server.repository.bookReviewCommentLike.BookReviewCommentLikeRepository;
import com.citalacki_dnevnik.server.repository.user.UserNotificationRepository;
import com.citalacki_dnevnik.server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.*;
import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.USER_NOT_FOUND;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookReviewCommentLikeServiceImpl implements BookReviewCommentLikeService {

    private final BookReviewCommentLikeRepository bookReviewCommentLikeRepository;
    private final BookReviewCommentRepository bookReviewCommentRepository;
    private final UserRepository userRepository;
    private final UserNotificationRepository userNotificationRepository;

    @Override
    @Transactional
    public BookReviewCommentLikeDTO create(BookReviewCommentLikeDTO bookReviewCommentLikeDTO) {
        BookReviewCommentLike like =
                bookReviewCommentLikeRepository.findByBookReviewCommentIdAndUserId(bookReviewCommentLikeDTO.getBookReviewCommentId(), bookReviewCommentLikeDTO.getUserId());
        if(like != null) {
            throw new BadRequestException(BOOK_REVIEW_COMMENT_LIKE_ALREADY_EXIST);
        }
        BookReviewCommentLike newBookReviewCommentLike;
        newBookReviewCommentLike = convertFromDTO(bookReviewCommentLikeDTO);
        newBookReviewCommentLike.setEntityStatus(EntityStatus.REGULAR);

        BookReviewCommentLike saved = bookReviewCommentLikeRepository.save(newBookReviewCommentLike);

        if(!saved.getUser().equals(saved.getBookReviewComment().getUser())) {
            UserNotification notification = new UserNotification();
            notification.setUser(saved.getBookReviewComment().getUser());
            notification.setText(
                    "korisnik '" + saved.getUser().getUsername() + "' je lajkovao vas komentar za recenziju '"
                            + saved.getBookReviewComment().getBookReview().getTitle() + "'"
            );
            notification.setUrl("/recenzije/sve/" + saved.getBookReviewComment().getBookReview().getId());
            notification.setEntityStatus(EntityStatus.REGULAR);
            userNotificationRepository.save(notification);
        }

        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public BookReviewCommentLikeDTO delete(Long bookReviewCommentId, Long userId) {
        BookReviewCommentLike like =
                bookReviewCommentLikeRepository.findByBookReviewCommentIdAndUserId(bookReviewCommentId, userId);
        if(like == null) {
            throw new BadRequestException(BOOK_REVIEW_COMMENT_LIKE_NOT_FOUND);
        }
        bookReviewCommentLikeRepository.delete(like);

        return convertToDTO(like);
    }

    @Override
    public BookReviewCommentLikeDTO convertToDTO(BookReviewCommentLike bookReviewCommentLike) {
        BookReviewCommentLikeDTO dto = new BookReviewCommentLikeDTO();
        dto.setId(bookReviewCommentLike.getId());

        if(bookReviewCommentLike.getBookReviewComment() != null) {
            dto.setBookReviewCommentId(bookReviewCommentLike.getBookReviewComment().getId());
        }

        if(bookReviewCommentLike.getUser() != null) {
            dto.setUserId(bookReviewCommentLike.getUser().getId());
        };

        return dto;
    }

    @Override
    public BookReviewCommentLike convertFromDTO(BookReviewCommentLikeDTO bookReviewCommentLikeDTO) {
        BookReviewCommentLike bookReviewCommentLike = new BookReviewCommentLike();
        bookReviewCommentLike.setId(bookReviewCommentLikeDTO.getId());

        if(bookReviewCommentLikeDTO.getBookReviewCommentId() != null) {
            BookReviewComment bookReviewComment = bookReviewCommentRepository.findByIdAndEntityStatus(bookReviewCommentLikeDTO.getBookReviewCommentId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(BOOK_REVIEW_COMMENT_NOT_FOUND));
            bookReviewCommentLike.setBookReviewComment(bookReviewComment);
        }

        if(bookReviewCommentLikeDTO.getUserId() != null) {
            User user = userRepository.findByIdAndEntityStatus(bookReviewCommentLikeDTO.getUserId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
            bookReviewCommentLike.setUser(user);
        }

        return bookReviewCommentLike;
    }

}
