package org.books.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Format;
import org.books.Model.enums.BookGenre;
import org.books.Model.enums.Language;
import org.books.Model.enums.PublicationStatus;
import org.books.utils.BookGenreConverter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Book extends Publication {
    private int isbn;
    @Convert(converter = BookGenreConverter.class)
    @Column(name = "genres", columnDefinition = "varchar(255) default NULL")
    private List<BookGenre> bookGenre;
    @Enumerated(EnumType.STRING)
    private Format format;

    public Book(String title, Language language, LocalDate publicationDate, int pageCount, String publisher, String author, String summary, PublicationStatus publicationStatus, int isbn, Format format, List<BookGenre> bookGenre) {
        super(title, language, publicationDate, pageCount, publisher, author, summary, publicationStatus);
        this.isbn = isbn;
        this.format = format;
        this.bookGenre = bookGenre;
    }

    public List<BookGenre> getBookGenres() {
        return this.bookGenre;
    }
}
