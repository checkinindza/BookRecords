package org.books.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.books.Model.User;
import org.books.StartGUI;
import org.books.hibernateControllers.CustomHibernate;
import org.books.utils.FxUtils;

import java.io.IOException;

public class Login {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    CustomHibernate customHibernate = new CustomHibernate(entityManagerFactory);

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;

    public void openRegisterWindow() throws IOException {
        StartGUI.changeScene("/org.books/register.fxml");
    }

    public void validateUser() throws IOException {
        User user = customHibernate.getUserByCredentials(loginField.getText(), pswField.getText());
        if (user != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/org.books/main.fxml"));
            Parent parent = fxmlLoader.load();
            Main main = fxmlLoader.getController();
            main.setData(entityManagerFactory, user);
            Scene scene = new Scene(parent);
            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.setTitle("Book exchange");
            stage.setScene(scene);
            stage.show();
        } else {
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.ERROR, "Incorrect data", "Incorrect login or password");
        }
    }
}
