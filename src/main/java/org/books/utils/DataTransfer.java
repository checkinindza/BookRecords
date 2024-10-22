package org.books.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter

public final class DataTransfer {
    private boolean addWasPressed;
    private String text;
    private List<?> list;
    private final static DataTransfer instance = new DataTransfer();

    public static DataTransfer getInstance() {
        return instance;
    }

    public boolean getAddWasPressed() {
        return this.addWasPressed;
    }
}
