package org.books.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.books.StartGUI;

import java.io.IOException;

public class Login {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;

    public void openNewWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/org.books/main.fxml"));
        Stage stage = (Stage) loginField.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Book exchange");
        stage.setScene(scene);
        stage.show();
    }
}
