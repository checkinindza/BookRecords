package org.books.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.books.Model.Client;
import org.books.hibernateControllers.CustomHibernate;
import org.books.utils.FxUtils;
import org.books.utils.PasswordUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Register {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField emailField;

    EntityManagerFactory entityManagerFactory;
    private CustomHibernate hibernate;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        hibernate = new CustomHibernate(entityManagerFactory);
    }

    public void createClient(ActionEvent event) {
        if (checkIfInputValid()) {
            return;
        } else {
            Client client = new Client(loginField.getText(), PasswordUtils.hashPassword(passwordField.getText()), "", "", emailField.getText());
            hibernate.create(client);
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.INFORMATION, "SUCCESS", "Account created successfully");
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public boolean checkIfInputValid() {
        return loginField.getText().isEmpty() && passwordField.getText().isEmpty() && emailField.getText().isEmpty();
    }

    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
