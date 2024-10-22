package org.books.Model.enums;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public enum BookGenre {
    FICTION,
    ACTION,
    ADVENTURE,
    BEACH_READ,
    CLASSIC,
    COMING_OF_AGE,
    DARK_ACADEMIA,
    DOMESTIC_FICTION,
    DYSTOPIAN,
    EROTICA,
    FAIRY_TALE,
    FAMILY_DRAMA,
    FANTASY,
    GRAPHIC_NOVEL,
    HISTORICAL_FICTION,
    HORROR;

    @Override
    public String toString() {
        String name = name().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).replace("_", " ");
    }
}
