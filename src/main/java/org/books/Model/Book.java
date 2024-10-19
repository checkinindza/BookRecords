package org.books.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Format;
import org.books.Model.enums.BookGenre;
import org.books.Model.enums.Language;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Book extends Publication {
    private String isbn;
    @Enumerated
    private BookGenre bookGenre;
    private int publicationYear;
    @Enumerated
    private Format format;
    private String Summary;

    public Book(String title, Language language, LocalDate publicationDate, int pageCount, String publisher, String author, String isbn, BookGenre bookGenre, int publicationYear, Format format, String summary) {
        super(title, language, publicationDate, pageCount, publisher, author);
        this.isbn = isbn;
        this.bookGenre = bookGenre;
        this.publicationYear = publicationYear;
        this.format = format;
        Summary = summary;
    }
}
