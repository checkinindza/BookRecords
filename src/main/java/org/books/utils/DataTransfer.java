package org.books.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public final class DataTransfer {
    private boolean addWasPressed;
    private String text;
    private final static DataTransfer instance = new DataTransfer();

    public static DataTransfer getInstance() {
        return instance;
    }

    public boolean getAddWasPressed() {
        return this.addWasPressed;
    }
}
