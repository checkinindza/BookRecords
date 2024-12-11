package org.books.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.Demographic;
import org.books.Model.enums.Language;
import org.books.Model.enums.MangaGenre;
import org.books.Model.enums.PublicationStatus;
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
    @Enumerated(EnumType.STRING)
    private Demographic demographic;
    @Convert(converter = MangaGenreConverter.class)
    @Column(name = "genres", columnDefinition = "varchar(255) default NULL")
    private List<MangaGenre> mangaGenres;
    private boolean isColor;

    public Manga(String title, Language language, LocalDate publicationDate, int pageCount, String publisher, String author, String summary, PublicationStatus publicationStatus, int jan, String illustrator, int volumeNumber, Demographic demographic, List<MangaGenre> mangaGenres, boolean isColor) {
        super(title, language, publicationDate, pageCount, publisher, author, summary, publicationStatus);
        this.jan = jan;
        this.illustrator = illustrator;
        this.volumeNumber = volumeNumber;
        this.demographic = demographic;
        this.mangaGenres = mangaGenres;
        this.isColor = isColor;
    }

    public boolean getIsColor() {
        return this.isColor;
    }

    public void setIsColor(boolean value) {
        this.isColor = value;
    }
}
