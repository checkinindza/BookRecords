package org.books.fxControllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class BorrowedBooksTableParameters {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty publicationTitle = new SimpleStringProperty();
    private ObjectProperty<LocalDate> publicationBorrowDate = new SimpleObjectProperty<>();

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getPublicationTitle() {
        return publicationTitle.get();
    }

    public SimpleStringProperty publicationTitleProperty() {
        return publicationTitle;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle.set(publicationTitle);
    }

    public LocalDate getPublicationBorrowDate() {
        return publicationBorrowDate.get();
    }

    public ObjectProperty<LocalDate> publicationBorrowDateProperty() {
        return publicationBorrowDate;
    }

    public void setPublicationBorrowDate(LocalDate publicationBorrowDate) {
        this.publicationBorrowDate.set(publicationBorrowDate);
    }
}
