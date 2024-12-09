package org.books.utils;

import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public final class DataTransfer {
    private boolean addWasPressed;
    private boolean updateWasPressed;
    private EntityManagerFactory entityManagerFactory;
    private String text;
    private Object object;
    private final static DataTransfer instance = new DataTransfer();

    public static DataTransfer getInstance() {
        return instance;
    }

    public boolean getAddWasPressed() {
        return this.addWasPressed;
    }

    public boolean getUpdateWasPressed() {
        return this.updateWasPressed;
    }
}
