package org.books.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.books.Model.Admin;
import org.books.Model.Client;
import org.books.Model.User;
import org.books.hibernateControllers.GenericHibernate;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Main implements Initializable {
    @FXML
    public ListView<User> userListField;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;
    @FXML
    public RadioButton clientChk;
    @FXML
    public RadioButton adminChk;
    @FXML
    public TextField addressField;
    @FXML
    public DatePicker bDate;
    @FXML
    public TextField phoneNum;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    GenericHibernate hibernate = new GenericHibernate(entityManagerFactory);
    User selectedUser = null;

    public void createNewUser() {
        if (clientChk.isSelected()) {
            Client client = new Client(loginField.getText(), pswField.getText(), nameField.getText(), surnameField.getText(), addressField.getText(), bDate.getValue());
            hibernate.create(client);
        } else {
            Admin admin = new Admin(loginField.getText(), pswField.getText(), nameField.getText(), surnameField.getText(), phoneNum.getText());
            hibernate.create(admin);
        }
        fillUserTable();
    }

    public void disableFields() {
        if (clientChk.isSelected()) {
            addressField.setDisable(false);
            bDate.setDisable(false);
            phoneNum.setDisable(true);
        } else {
            addressField.setDisable(true);
            bDate.setDisable(true);
            phoneNum.setDisable(false);
        }
    }

    public void fillUserTable() {
        userListField.getItems().clear();
        List<User> userList = hibernate.getAllRecords(User.class);
        userListField.getItems().addAll(userList);
    }

    public void loadUserData() {
        User user = userListField.getSelectionModel().getSelectedItem();
        User latestUser = hibernate.getEntityById(User.class, user.getId());
        if (latestUser instanceof Client) {
            Client client = (Client) latestUser;
            nameField.setText(client.getName());
            surnameField.setText(client.getSurname());
            ///
        } else {
            Admin admin = (Admin) latestUser;
            nameField.setText(admin.getName());
            surnameField.setText(admin.getSurname());
            ///
        }
    }

    public void updateUser() {
        if (selectedUser instanceof Client) {
            Client client = hibernate.getEntityById(Client.class, selectedUser.getId());
            client.setName(nameField.getText());
            client.setLogin(loginField.getText());
            client.setSurname(surnameField.getText());

            hibernate.update(client);
        } else {
            Admin admin = hibernate.getEntityById(Admin.class, selectedUser.getId());
            admin.setName(nameField.getText());
            ///
            hibernate.update(admin);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillUserTable();
    }

    public void deleteUser() {
        hibernate.delete(User.class, selectedUser.getId());
        fillUserTable();
    }
}
