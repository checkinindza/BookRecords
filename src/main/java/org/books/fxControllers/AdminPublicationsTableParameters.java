package org.books.fxControllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.books.Model.enums.PublicationStatus;

public class AdminPublicationsTableParameters {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty title = new SimpleStringProperty();
    private SimpleStringProperty publicationOwner = new SimpleStringProperty();
    private SimpleStringProperty publicationBorrower = new SimpleStringProperty();
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

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getPublicationOwner() {
        return publicationOwner.get();
    }

    public SimpleStringProperty publicationOwnerProperty() {
        return publicationOwner;
    }

    public void setPublicationOwner(String publicationOwner) {
        this.publicationOwner.set(publicationOwner);
    }

    public String getPublicationBorrower() {
        return publicationBorrower.get();
    }

    public SimpleStringProperty publicationBorrowerProperty() {
        return publicationBorrower;
    }

    public void setPublicationBorrower(String publicationBorrower) {
        this.publicationBorrower.set(publicationBorrower);
    }

    public PublicationStatus getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(PublicationStatus publicationStatus) {
        this.publicationStatus = publicationStatus;
    }
}
