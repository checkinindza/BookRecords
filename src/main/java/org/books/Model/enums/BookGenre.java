package org.books.Model.enums;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.books.Interfaces.HasSelectedProperty;

public enum BookGenre implements HasSelectedProperty {
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

    private final BooleanProperty on = new SimpleBooleanProperty(false);

    @Override
    public BooleanProperty selectedProperty() {
        return on;
    }

    public void setSelected(boolean selected) {
        this.on.set(selected);
    }

    @Override
    public String toString() {
        String name = name().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).replace("_", " ");
    }
}
