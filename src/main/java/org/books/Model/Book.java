package org.books.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Format;
import org.books.Model.enums.BookGenre;
import org.books.Model.enums.Language;
import org.books.utils.BookGenreConverter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Book extends Publication {
    private String isbn;
    @Convert(converter = BookGenreConverter.class)
    @Column(name = "genres", nullable = false)
    @SuppressWarnings("JpaDataSourceORMInspection")
    private List<BookGenre> bookGenre;
    private int publicationYear;
    @Enumerated
    private Format format;

    public Book(String title, Language language, LocalDate publicationDate, int pageCount, String publisher, String author, String summary, String isbn, List<BookGenre> bookGenre, int publicationYear, Format format) {
        super(title, language, publicationDate, pageCount, publisher, author, summary);
        this.isbn = isbn;
        this.bookGenre = bookGenre;
        this.publicationYear = publicationYear;
        this.format = format;
    }
}
