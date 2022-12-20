package com.citalacki_dnevnik.server.model.bookGenre;

import com.citalacki_dnevnik.server.model.AbstractStatusEntity;
import com.citalacki_dnevnik.server.model.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_genre")
public class BookGenre extends AbstractStatusEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    private List<Book> books = new ArrayList<>();

}
