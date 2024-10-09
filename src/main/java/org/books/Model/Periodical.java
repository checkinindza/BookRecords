package org.books.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Frequency;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Periodical extends Publication {
    private int issn;
    private int issueNumber;
    private LocalDate publicationDate;
    private String editor;
    @Enumerated
    private Frequency frequency;
    private String publisher;

    public Periodical(int id, String title, int issn, int issueNumber, LocalDate publicationDate, String editor, Frequency frequency, String publisher) {
        super(id, title);
        this.issn = issn;
        this.issueNumber = issueNumber;
        this.publicationDate = publicationDate;
        this.editor = editor;
        this.frequency = frequency;
        this.publisher = publisher;
    }

    public Periodical(String title, int issn, int issueNumber, LocalDate publicationDate, String editor, Frequency frequency, String publisher) {
        super(title);
        this.issn = issn;
        this.issueNumber = issueNumber;
        this.publicationDate = publicationDate;
        this.editor = editor;
        this.frequency = frequency;
        this.publisher = publisher;
    }
}
