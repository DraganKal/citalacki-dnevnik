package com.citalacki_dnevnik.server.service.bookReviewComment;

import com.citalacki_dnevnik.server.config.error.BadRequestException;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.bookReview.BookReview;
import com.citalacki_dnevnik.server.model.bookReviewComment.BookReviewComment;
import com.citalacki_dnevnik.server.model.bookReviewCommentLike.BookReviewCommentLike;
import com.citalacki_dnevnik.server.model.bookReviewLike.BookReviewLike;
import com.citalacki_dnevnik.server.model.dto.bookReviewComment.BookReviewCommentDTO;
import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.model.user.UserNotification;
import com.citalacki_dnevnik.server.repository.bookReview.BookReviewRepository;
import com.citalacki_dnevnik.server.repository.bookReviewComment.BookReviewCommentRepository;
import com.citalacki_dnevnik.server.repository.bookReviewCommentLike.BookReviewCommentLikeRepository;
import com.citalacki_dnevnik.server.repository.user.UserNotificationRepository;
import com.citalacki_dnevnik.server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookReviewCommentServiceImpl implements BookReviewCommentService {

    private final BookReviewCommentRepository bookReviewCommentRepository;
    private final BookReviewRepository bookReviewRepository;
    private final UserRepository userRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final BookReviewCommentLikeRepository bookReviewCommentLikeRepository;

    @Override
    public Page<BookReviewCommentDTO> getAllByBookReviewIdPageable(Long bookReviewId, Pageable pageable) {
        return bookReviewCommentRepository.findAllByBookReviewIdAndEntityStatus(bookReviewId,EntityStatus.REGULAR, pageable)
                .map(this::convertToDTO);
    }

    @Override
    public BookReviewCommentDTO getById(Long id) {
        BookReviewComment bookReviewComment = bookReviewCommentRepository.findByIdAndEntityStatus(id, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(BOOK_REVIEW_COMMENT_NOT_FOUND));
        return convertToDTO(bookReviewComment);
    }

    @Override
    @Transactional
    public BookReviewCommentDTO create(BookReviewCommentDTO bookReviewCommentDTO) {
        BookReviewComment newBookReviewComment;
        newBookReviewComment = convertFromDTO(bookReviewCommentDTO);
        newBookReviewComment.setEntityStatus(EntityStatus.REGULAR);

        BookReviewComment saved = bookReviewCommentRepository.save(newBookReviewComment);

        if(!saved.getUser().equals(saved.getBookReview().getUser())) {
            UserNotification notification = new UserNotification();
            notification.setUser(saved.getBookReview().getUser());
            notification.setText(
                    "korisnik '" + saved.getUser().getUsername() + "' je komentarisao vasu recenziju za knjigu '"
                            + saved.getBookReview().getBook().getName() + "'"
            );
            notification.setUrl("/recenzije/sve/" + saved.getBookReview().getId());
            notification.setEntityStatus(EntityStatus.REGULAR);
            userNotificationRepository.save(notification);
        }

        return convertToDTO(saved);
    }

    @Override
    public BookReviewCommentDTO convertToDTO(BookReviewComment bookReviewComment) {
        BookReviewCommentDTO dto = new BookReviewCommentDTO();
        dto.setId(bookReviewComment.getId());
        dto.setText(bookReviewComment.getText());

        if(bookReviewComment.getBookReview() != null) {
            dto.setBookReviewId(bookReviewComment.getBookReview().getId());
        }

        if(bookReviewComment.getUser() != null) {
            dto.setUserId(bookReviewComment.getUser().getId());
            dto.setUserUsername(bookReviewComment.getUser().getUsername());
        }

        dto.setCreatedTime(LocalDateTime.ofInstant(bookReviewComment.getCreatedDate(), ZoneId.of("Europe/Belgrade")));

        List<BookReviewCommentLike> likes = bookReviewCommentLikeRepository.findAllByBookReviewCommentId(bookReviewComment.getId());
        dto.setLikes(likes.size());
        List<Long> likesUserIds = new ArrayList<>();
        for(BookReviewCommentLike like : likes) {
            likesUserIds.add(like.getUser().getId());
        }
        dto.setLikesUserIds(likesUserIds);

        return dto;
    }

    @Override
    public BookReviewComment convertFromDTO(BookReviewCommentDTO bookReviewCommentDTO) {
        BookReviewComment bookReviewComment = new BookReviewComment();
        bookReviewComment.setId(bookReviewCommentDTO.getId());
        bookReviewComment.setText(bookReviewCommentDTO.getText());

        if(bookReviewCommentDTO.getBookReviewId() != null) {
            BookReview bookReview = bookReviewRepository.findByIdAndEntityStatus(bookReviewCommentDTO.getBookReviewId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(BOOK_REVIEW_NOT_FOUND));
            bookReviewComment.setBookReview(bookReview);
        }

        if(bookReviewCommentDTO.getUserId() != null) {
            User user = userRepository.findByIdAndEntityStatus(bookReviewCommentDTO.getUserId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
            bookReviewComment.setUser(user);
        }

        return bookReviewComment;
    }


}
