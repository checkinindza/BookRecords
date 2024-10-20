package org.books.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Demographic;
import org.books.Model.enums.Language;
import org.books.Model.enums.MangaGenre;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Manga extends Publication {
    private int jan;
    private String illustrator;
    private int volumeNumber;
    @Enumerated
    private Demographic demographic;
    @Enumerated
    private MangaGenre mangaGenre;
    private boolean isColor;

    public Manga(String title, Language language, LocalDate publicationDate, int pageCount, String publisher, String author, String summary, int jan, String illustrator, int volumeNumber, Demographic demographic, MangaGenre mangaGenre, boolean isColor) {
        super(title, language, publicationDate, pageCount, publisher, author, summary);
        this.jan = jan;
        this.illustrator = illustrator;
        this.volumeNumber = volumeNumber;
        this.demographic = demographic;
        this.mangaGenre = mangaGenre;
        this.isColor = isColor;
    }
}
