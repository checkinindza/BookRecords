package org.books.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Demographic;
import org.books.Model.enums.Language;
import org.books.Model.enums.MangaGenre;
import org.books.utils.MangaGenreConverter;

import java.time.LocalDate;
import java.util.List;

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
    @Convert(converter = MangaGenreConverter.class)
    @Column(name = "genres", nullable = false)
    private List<MangaGenre> mangaGenres;
    private boolean isColor;

    public Manga(String title, Language language, LocalDate publicationDate, int pageCount, String publisher, String author, String summary, int jan, String illustrator, int volumeNumber, Demographic demographic, List<MangaGenre> mangaGenres, boolean isColor) {
        super(title, language, publicationDate, pageCount, publisher, author, summary);
        this.jan = jan;
        this.illustrator = illustrator;
        this.volumeNumber = volumeNumber;
        this.demographic = demographic;
        this.mangaGenres = mangaGenres;
        this.isColor = isColor;
    }
}
