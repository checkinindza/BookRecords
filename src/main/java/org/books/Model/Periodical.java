package org.books.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Frequency;
import org.books.Model.enums.Language;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Periodical extends Publication {
    private int issn;
    private int issueNumber;
    private String editor;
    @Enumerated
    private Frequency frequency;

    public Periodical(String title, Language language, LocalDate publicationDate, int pageCount, String publisher, String author, int issn, int issueNumber, String editor, Frequency frequency) {
        super(title, language, publicationDate, pageCount, publisher, author);
        this.issn = issn;
        this.issueNumber = issueNumber;
        this.editor = editor;
        this.frequency = frequency;
    }
}
