package org.books.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Language;
import org.books.Model.enums.PublicationStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public abstract class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String title;
    @Enumerated(EnumType.STRING)
    protected Language language;
    protected LocalDate publicationDate;
    protected int pageCount;
    @ManyToOne
    protected Client owner;
    @ManyToOne
    protected Client borrowerClient;
    protected String publisher;
    protected String author;
    protected String summary;
    @Enumerated(EnumType.STRING)
    protected PublicationStatus publicationStatus;
    @Column(insertable = false, updatable = false)
    protected String dtype;
    protected LocalDate requestDate;

    public Publication(String title, Language language, LocalDate publicationDate, int pageCount, String publisher, String author, String summary, PublicationStatus publicationStatus) {
        this.title = title;
        this.language = language;
        this.publicationDate = publicationDate;
        this.pageCount = pageCount;
        this.publisher = publisher;
        this.author = author;
        this.summary = summary;
        this.publicationStatus = publicationStatus;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
