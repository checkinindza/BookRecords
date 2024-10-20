package org.books.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Language;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public abstract class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Enumerated
    private Language language;
    private LocalDate publicationDate;
    private int pageCount;
    private String publisher;
    private String author;
    private String summary;
    @Column(insertable = false, updatable = false)
    private String dtype;

    public Publication(String title, Language language, LocalDate publicationDate, int pageCount, String publisher, String author, String summary) {
        this.title = title;
        this.language = language;
        this.publicationDate = publicationDate;
        this.pageCount = pageCount;
        this.publisher = publisher;
        this.author = author;
        this.summary = summary;
    }
}
