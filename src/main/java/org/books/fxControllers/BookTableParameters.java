package org.books.fxControllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;
import org.books.Model.enums.PublicationStatus;

import java.time.LocalDate;

public class BookTableParameters {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleIntegerProperty identificationNumber = new SimpleIntegerProperty();
    private final SimpleStringProperty publicationTitle = new SimpleStringProperty();
    private final SimpleStringProperty publicationUser = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> publicationRequestDate = new SimpleObjectProperty<>();
    @Setter
    @Getter
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

    public int getIdentificationNumber() {
        return identificationNumber.get();
    }

    public SimpleIntegerProperty identificationNumberProperty() {
        return identificationNumber;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber.set(identificationNumber);
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
}
