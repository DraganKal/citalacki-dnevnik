package com.citalacki_dnevnik.server.service.book;

import com.citalacki_dnevnik.server.config.error.BadRequestException;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.book.AskForBook;
import com.citalacki_dnevnik.server.model.book.Book;
import com.citalacki_dnevnik.server.model.bookGenre.BookGenre;
import com.citalacki_dnevnik.server.model.bookReview.BookReview;
import com.citalacki_dnevnik.server.model.dto.book.BookDTO;
import com.citalacki_dnevnik.server.model.dto.bookGenre.BookGenreDTO;
import com.citalacki_dnevnik.server.model.user.UserNotification;
import com.citalacki_dnevnik.server.repository.book.AskForBookRepository;
import com.citalacki_dnevnik.server.repository.book.BookRepository;
import com.citalacki_dnevnik.server.repository.bookGenre.BookGenreRepository;
import com.citalacki_dnevnik.server.repository.bookReview.BookReviewRepository;
import com.citalacki_dnevnik.server.repository.user.UserNotificationRepository;
import com.citalacki_dnevnik.server.service.bookGenre.BookGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookGenreRepository bookGenreRepository;
    private final BookGenreService bookGenreService;
    private final AskForBookRepository askForBookRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final BookReviewRepository bookReviewRepository;

//    private final SimpMessagingTemplate template;

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findByEntityStatus(EntityStatus.REGULAR)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookDTO> findAllPageable(Optional<String> filter, Optional<Long> bookGenreId, Pageable pageable) {
        if(bookGenreId.isPresent()) {
            return bookRepository.findAllByBookGenreId(bookGenreId.get(), pageable).map(this::convertToDTO);
        } else {
            return bookRepository.findAllByNameAndAuthor(filter.get(), pageable).map(this::convertToDTO);
        }
    }

    @Override
    public BookDTO getBook(Long id) {
        Book book = bookRepository.findByIdAndEntityStatus(id, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(BOOK_NOT_FOUND));
        return convertToDTO(book);
    }

    @Override
    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookRepository.findByNameAndEntityStatus(bookDTO.getName(), EntityStatus.REGULAR);
        if(book != null) {
            throw new BadRequestException(BOOK_NAME_ALREADY_EXIST);
        }
        Book newBook = new Book();
        newBook = convertFromDTO(bookDTO);
        newBook.setEntityStatus(EntityStatus.REGULAR);

        Book savedBook = bookRepository.save(newBook);
        return convertToDTO(savedBook);
    }

    @Override
    @Transactional
    public BookDTO createBookByRequest(Long askForBookId, BookDTO bookDTO) {
        AskForBook askForBook = askForBookRepository.findByIdAndEntityStatus(askForBookId, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(ASK_FOR_BOOK_NOT_FOUND));

        askForBook.setDone(true);
        askForBookRepository.save(askForBook);
        BookDTO createdBook = createBook(bookDTO);

        UserNotification notification = new UserNotification();
        notification.setUser(askForBook.getUser());
        notification.setText("Dodali smo knjigu '" + createdBook.getName() + "', koju ste zatrazili. - Vas Citalacki Dnevnik");
        notification.setUrl("/knjige/sve/" + createdBook.getId());
        notification.setEntityStatus(EntityStatus.REGULAR);
        userNotificationRepository.save(notification);

        return createdBook;
    }

    @Override
    @Transactional
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book bookForUpdate = bookRepository.findByIdAndEntityStatus(id, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(BOOK_NOT_FOUND));
//        todo validateBookForm();
        bookForUpdate.setName(bookDTO.getName());
        bookForUpdate.setAuthor(bookDTO.getAuthor());
        bookForUpdate.setPublishedYear(bookDTO.getPublishedYear());
        bookForUpdate.setDescription(bookDTO.getDescription());
        bookForUpdate.setImageUrl(bookDTO.getImageUrl());

        List<BookGenre> genres = new ArrayList<>();
        if(!bookDTO.getGenresIds().isEmpty()) {
            for(Long genreId : bookDTO.getGenresIds()) {
                BookGenre genre = bookGenreRepository.findByIdAndEntityStatus(genreId, EntityStatus.REGULAR)
                        .orElseThrow(() -> new BadRequestException(BOOK_GENRE_NOT_FOUND));
                genres.add(genre);
            }
        }
        bookForUpdate.setGenres(genres);

        Book updatedBook = bookRepository.save(bookForUpdate);
        return convertToDTO(updatedBook);
    }

    @Override
    public BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublishedYear(book.getPublishedYear());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setImageUrl(book.getImageUrl());

        List<String> genreNames = new ArrayList<>();
        List<Long> genreIds = new ArrayList<>();
        List<BookGenreDTO> genresDTOS = new ArrayList<>();
        if(!book.getGenres().isEmpty()) {
            for(BookGenre genre : book.getGenres()) {
                genreNames.add(genre.getName());
                genreIds.add(genre.getId());
                genresDTOS.add(bookGenreService.convertToDTO(genre));
            }
        }
        bookDTO.setGenresNames(genreNames);
        bookDTO.setGenresIds(genreIds);
        bookDTO.setGenresDTOS(genresDTOS);

        List<BookReview> reviews = bookReviewRepository.findAllByBookIdAndEntityStatus(book.getId(), EntityStatus.REGULAR);
        double sumRates = 0;
        if(reviews.size() > 0) {
            for(BookReview review : reviews) {
                sumRates += review.getBookRating();
            }
            double rate = sumRates / reviews.size();
            double roundRate = Math.round(rate * 100.0) / 100.0;
            bookDTO.setRate(roundRate);
            if(roundRate < 1.5) {
                bookDTO.setRating(1);
            } else if (roundRate < 2.51) {
                bookDTO.setRating(2);
            } else if (roundRate < 3.51) {
                bookDTO.setRating(3);
            } else if (roundRate < 4.51) {
                bookDTO.setRating(4);
            } else {
                bookDTO.setRating(5);
            }
        } else {
            bookDTO.setRate(0);
            bookDTO.setRating(0);
        }

        bookDTO.setReviewNumbers(reviews.size());

        return bookDTO;
    }

    @Override
    public Book convertFromDTO(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublishedYear(bookDTO.getPublishedYear());
        book.setDescription(bookDTO.getDescription());
        book.setImageUrl(bookDTO.getImageUrl());
        List<BookGenre> genres = new ArrayList<>();

        if(!bookDTO.getGenresIds().isEmpty()) {
            for(Long genreId : bookDTO.getGenresIds()) {
                BookGenre genre = bookGenreRepository.findByIdAndEntityStatus(genreId, EntityStatus.REGULAR)
                        .orElseThrow(() -> new BadRequestException(BOOK_GENRE_NOT_FOUND));
                genres.add(genre);
            }
        }
        book.setGenres(genres);
        return book;
    }

}
