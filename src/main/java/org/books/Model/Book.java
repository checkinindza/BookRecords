package org.books.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Format;
import org.books.Model.enums.Genre;
import org.books.Model.enums.Language;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Book extends Publication {
    private String publisher;
    private String isbn;
    private Genre genre;
    private int pageCount;
    private Language language;
    private int publicationYear;
    private Format format;
    private String Summary;

    public Book(int id, String title, String publisher, String isbn, Genre genre, int pageCount, Language language, int publicationYear, Format format, String summary) {
        super(id, title);
        this.publisher = publisher;
        this.isbn = isbn;
        this.genre = genre;
        this.pageCount = pageCount;
        this.language = language;
        this.publicationYear = publicationYear;
        this.format = format;
        Summary = summary;
    }

    public Book(String title, String summary, Format format, int publicationYear, Language language, int pageCount, Genre genre, String isbn, String publisher) {
        super(title);
        Summary = summary;
        this.format = format;
        this.publicationYear = publicationYear;
        this.language = language;
        this.pageCount = pageCount;
        this.genre = genre;
        this.isbn = isbn;
        this.publisher = publisher;
    }
}
