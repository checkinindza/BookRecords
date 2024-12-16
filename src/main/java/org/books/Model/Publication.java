package org.books.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Language;
import org.books.Model.enums.PublicationStatus;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public abstract class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected int pageCount;
    protected String title;
    protected LocalDate publicationDate;
    protected LocalDate requestDate;
    @ManyToOne
    protected Client owner;
    @ManyToOne
    protected Client borrowerClient;
    protected String publisher;
    protected String author;
    protected String summary;
    @Column(insertable = false, updatable = false)
    protected String dtype;
    @Enumerated(EnumType.STRING)
    protected PublicationStatus publicationStatus;
    @Enumerated(EnumType.STRING)
    protected Language language;
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    protected List<PeriodicRecord> records;

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

    public Publication(int pageCount, String title, LocalDate publicationDate, String publisher, String author, String summary, PublicationStatus publicationStatus, Language language, Client owner) {
        this.pageCount = pageCount;
        this.title = title;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.author = author;
        this.summary = summary;
        this.publicationStatus = publicationStatus;
        this.language = language;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
