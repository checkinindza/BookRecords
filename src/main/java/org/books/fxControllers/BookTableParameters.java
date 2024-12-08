package org.books.fxControllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.books.Model.enums.PublicationStatus;

import java.time.LocalDate;

public class BookTableParameters {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty publicationTitle = new SimpleStringProperty();
    private SimpleStringProperty publicationUser = new SimpleStringProperty();
    private ObjectProperty<LocalDate> publicationRequestDate = new SimpleObjectProperty<>();
    private PublicationStatus publicationStatus;

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

    public String getPublicationUser() {
        return publicationUser.get();
    }

    public SimpleStringProperty publicationUserProperty() {
        return publicationUser;
    }

    public void setPublicationUser(String publicationUser) {
        this.publicationUser.set(publicationUser);
    }

    public LocalDate getPublicationRequestDate() {
        return publicationRequestDate.get();
    }

    public ObjectProperty<LocalDate> publicationRequestDateProperty() {
        return publicationRequestDate;
    }

    public void setPublicationRequestDate(LocalDate publicationRequestDate) {
        this.publicationRequestDate.set(publicationRequestDate);
    }

    public PublicationStatus getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(PublicationStatus publicationStatus) {
        this.publicationStatus = publicationStatus;
    }
}
