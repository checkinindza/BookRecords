package org.books.fxControllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class UserTableParameters {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty username = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty surname = new SimpleStringProperty();
    private SimpleStringProperty userAddress = new SimpleStringProperty();
    private SimpleStringProperty userPhone = new SimpleStringProperty();
    private SimpleStringProperty userEmail = new SimpleStringProperty();
    private ObjectProperty<LocalDate> userBirthDate = new SimpleObjectProperty<>();

    public LocalDate getUserBirthDate() {
        return userBirthDate.get();
    }

    public ObjectProperty<LocalDate> userBirthDateProperty() {
        return userBirthDate;
    }

    public void setUserBirthDate(LocalDate userBirthDate) {
        this.userBirthDate.set(userBirthDate);
    }

    public String getUserEmail() {
        return userEmail.get();
    }

    public SimpleStringProperty userEmailProperty() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail.set(userEmail);
    }

    public String getUserPhone() {
        return userPhone.get();
    }

    public SimpleStringProperty userPhoneProperty() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone.set(userPhone);
    }

    public String getUserAddress() {
        return userAddress.get();
    }

    public SimpleStringProperty userAddressProperty() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress.set(userAddress);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }
}
