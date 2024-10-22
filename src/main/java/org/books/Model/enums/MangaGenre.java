package org.books.Model.enums;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.books.Interfaces.HasSelectedProperty;

public enum MangaGenre implements HasSelectedProperty {
    ACTION,
    ADVENTURE,
    COMEDY,
    DRAMA,
    FANTASY,
    MARTIAL_ARTS,
    SHOUNEN,
    HORROR,
    SUPERNATURAL,
    HAREM,
    PSYCHOLOGICAL,
    ROMANCE,
    SCHOOL_LIFE,
    SHOUJO,
    MYSTERY,
    SCI_FI,
    SEINEN,
    TRAGEDY,
    ECCHI,
    SPORTS,
    SLICE_OF_LIFE,
    MATURE,
    SHOUJO_AI,
    WEBTOONS,
    DOUJINSHI,
    ONE_SHOT,
    SMUT,
    YAOI,
    JOSEI,
    HISTORICAL,
    SHOUNEN_AI,
    GENDER_BENDER,
    ADULT,
    YURI,
    MECHA,
    LOLICON,
    SHOTACON;

    private final BooleanProperty on = new SimpleBooleanProperty(false);

    @Override
    public BooleanProperty selectedProperty() {
        return on;
    }

    public void setSelected() {
        this.on.set(false);
    }

    @Override
    public String toString() {
        String name = name().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).replace("_", " ");
    }
}
