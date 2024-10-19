package org.books.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.books.Model.User;
import org.books.StartGUI;
import org.books.hibernateControllers.GenericHibernate;
import org.books.utils.FxUtils;

import java.io.IOException;

public class Login {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    GenericHibernate hibernate = new GenericHibernate(entityManagerFactory);

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;

    public void openMainWindow() throws IOException {
   /*     if (checkUserInfo()) {
            return;
        }*/
        StartGUI.changeScene("/org.books/main.fxml");
    }

    public void openRegisterWindow() throws IOException {
        StartGUI.changeScene("/org.books/register.fxml");
    }

    public boolean checkUserInfo() {
        boolean incorrectUserInfo = false;
        User user = hibernate.getEntityByCriteria(User.class, "login", loginField.getText());
        if (user == null) {
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.ERROR, "Not found", "User not found");
            incorrectUserInfo = true;
        } else if (!user.getPassword().equals(pswField.getText())) {
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.ERROR, "Incorrect password", "Password is incorrect");
            incorrectUserInfo = true;
        }

        return incorrectUserInfo;
    }
}
