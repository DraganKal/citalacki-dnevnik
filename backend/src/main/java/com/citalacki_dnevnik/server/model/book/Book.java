package com.citalacki_dnevnik.server.model.book;

import com.citalacki_dnevnik.server.model.AbstractStatusEntity;
import com.citalacki_dnevnik.server.model.bookGenre.BookGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book extends AbstractStatusEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "author", length = 200, nullable = false)
    private String author;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany
    @JoinTable(name = "book_book_genre",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_genre_id")})
    private List<BookGenre> genres;


}
