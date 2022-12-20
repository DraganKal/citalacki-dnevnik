package com.citalacki_dnevnik.server.config.error;

/**
    Error constants. Mostly used for calling errors for BadRequestException
 */
public class ErrorMessageConstants {

    public static final String USER_NOT_FOUND_BY_USERNAME = "Korisnik pod korisnickim imenom nije pronadjen";
    public static final String USER_NOT_FOUND = "Korisnik nije pronadjen";
    public static final String USERNAME_ALREADY_EXIST = "Korisnicko ime se vec koristi";
    public static final String USER_NOT_FOUND_BY_EMAIL = "Korisnik nije pronadjen preko email-a";
    public static final String FILE_UPLOAD_ERROR = "FILE_UPLOAD_ERROR";
    public static final String PASSWORD_DOESNT_MATCH = "Lozinke se ne poklapaju";

    public static final String BOOK_GENRE_NOT_FOUND = "Zanr nije pronadjen";
    public static final String BOOK_NAME_ALREADY_EXIST = "Knjiga pod tim imenom vec postoji";
    public static final String BOOK_GENRE_NAME_ALREADY_EXIST = "Zanr pod tim imenom vec postoji";
    public static final String BOOK_NOT_FOUND = "Nismo pronasli knjigu";
    public static final String ASK_FOR_BOOK_NOT_FOUND = "Nismo pronasli zahtev";
    public static final String USER_NOTIFICATION_NOT_FOUND = "Nismo pronasli obavestenje";
    public static final String USER_NOTIFICATIONS_NOT_FOUND = "Nismo pronasli obavestenja";
    public static final String BOOK_REVIEW_NOT_FOUND = "Nismo pronasli recenziju";
    public static final String BOOK_REVIEW_LIKE_ALREADY_EXIST = "Vec ste lajkovali ovu recenziju";

    public static final String BOOK_REVIEW_LIKE_NOT_FOUND = "Nismo pronasli lajk";
    public static final String BOOK_REVIEW_COMMENT_LIKE_ALREADY_EXIST = "Vec ste lajkovali ovaj komentar";
    public static final String BOOK_REVIEW_COMMENT_LIKE_NOT_FOUND = "Nismo pronasli lajk";
    public static final String BOOK_REVIEW_COMMENT_NOT_FOUND = "Nismo pronasli komentar";
    public static final String USER_BOOK_ALREADY_EXIST = "Vec ste dodali ovu knjigu na policu";
    public static final String USER_BOOK_NOT_FOUND = "Nismo pronasli knjigu na polici";

}
