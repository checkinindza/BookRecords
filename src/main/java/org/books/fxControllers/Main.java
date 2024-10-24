package org.books.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.books.Model.Admin;
import org.books.Model.Client;
import org.books.Model.Publication;
import org.books.Model.User;
import org.books.StartGUI;
import org.books.hibernateControllers.GenericHibernate;
import org.books.utils.DataPopulator;
import org.books.utils.DataTransfer;
import org.books.utils.FxUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Main implements Initializable {
    // JAVA FX ELEMENTS

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
    @FXML
    public TextField emailField;
    @FXML
    public Label emailFormatWarningLabel;
    @FXML
    public Label phoneNumValidLabel;
    @FXML
    public Label invalidInputLabel;
    @FXML
    public Label noSelectionLabel;
    @FXML
    public Label bDateFieldCheck;
    @FXML
    public AnchorPane usersManagePane;
    @FXML
    public AnchorPane publicationsPane;
    @FXML
    public ListView<Object> publicationsListField;
    @FXML
    public ComboBox<String> publicationTypeComboBox;
    @FXML
    public Label typeNotSelectedWarningLabel;

    // END OF JAVA FX ELEMENTS

    private User selectedUser = null;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    GenericHibernate hibernate = new GenericHibernate(entityManagerFactory);
    DataPopulator dataPopulator = new DataPopulator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataPopulator.fillTableWithAllRecords(userListField, User.class);
        dataPopulator.fillComboBox(publicationTypeComboBox, dataPopulator.getChildren(Publication.class));
    }

    // USER TAB

    public void createNewUser() {
        if (!isInputValid()) {
            return;
        } else {
            if (clientChk.isSelected()) {
                Client client = new Client(loginField.getText(), pswField.getText(), nameField.getText(), surnameField.getText(), emailField.getText(), bDate.getValue(), addressField.getText());
                hibernate.create(client);
            } else {
                Admin admin = new Admin(loginField.getText(), pswField.getText(), nameField.getText(), surnameField.getText(), emailField.getText(), phoneNum.getText());
                hibernate.create(admin);
            }
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.INFORMATION, "SUCCESS", "User created successfully");
            dataPopulator.fillTableWithAllRecords(userListField, User.class);
        }
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

    public void loadUserData() {
        User user = userListField.getSelectionModel().getSelectedItem();
        User latestUser = hibernate.getEntityById(User.class, user.getId());
        if (latestUser instanceof Client) {
            disableFields();
            Client client = (Client) latestUser;
            clientChk.setSelected(true);
            nameField.setText(client.getName());
            surnameField.setText(client.getSurname());
            loginField.setText(client.getLogin());
            pswField.setText(client.getPassword());
            bDate.setValue(client.getBirthDate());
            addressField.setText(client.getAddress());
            emailField.setText(client.getEmail());
        } else {
            disableFields();
            Admin admin = (Admin) latestUser;
            adminChk.setSelected(true);
            nameField.setText(admin.getName());
            surnameField.setText(admin.getSurname());
            loginField.setText(admin.getLogin());
            pswField.setText(admin.getPassword());
            phoneNum.setText(admin.getPhoneNum());
            emailField.setText(admin.getEmail());
        }
    }

    public void updateUser() {
        selectedUser = userListField.getSelectionModel().getSelectedItem();
        if (!checkIfUserSelected()) {
            return;
        } else {
            if (!isInputValid()) {
                return;
            } else {
                invalidInputLabel.setDisable(false);
                if (selectedUser instanceof Client) {
                    Client client = hibernate.getEntityById(Client.class, selectedUser.getId());
                    client.setName(nameField.getText());
                    client.setLogin(loginField.getText());
                    client.setSurname(surnameField.getText());
                    client.setAddress(addressField.getText());
                    client.setBirthDate(bDate.getValue());
                    client.setPassword(pswField.getText());
                    client.setEmail(emailField.getText());
                    hibernate.update(client);
                } else {
                    Admin admin = hibernate.getEntityById(Admin.class, selectedUser.getId());
                    admin.setName(nameField.getText());
                    admin.setSurname(surnameField.getText());
                    admin.setLogin(loginField.getText());
                    admin.setPassword(pswField.getText());
                    admin.setPhone(phoneNum.getText());
                    admin.setEmail(emailField.getText());
                    hibernate.update(admin);
                }
            }
        }
        FxUtils.generateAlertWithoutHeader(Alert.AlertType.INFORMATION, "Success", "User updated successfully");
    }

    public void deleteUser() {
        selectedUser = userListField.getSelectionModel().getSelectedItem();
        if (!checkIfUserSelected()) {
            return;
        } else {
            hibernate.delete(User.class, selectedUser.getId());
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.INFORMATION, "Success", "User deleted successfully");
            dataPopulator.fillTableWithAllRecords(userListField, User.class);
        }
    }

    private boolean isInputValid() {
        boolean isValid = true;

        // Check email field
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-z]{2,}");
        if (!emailPattern.matcher(emailField.getText()).matches() || emailField.getText().isEmpty()) {
            isValid = false;
            emailFormatWarningLabel.setVisible(true);
        } else {
            emailFormatWarningLabel.setVisible(false);
        }

        // Check phone number field
        if (selectedUser instanceof Admin) {
            Pattern phoneNumPattern = Pattern.compile("\\+[0-9]{11}");
            if (!phoneNumPattern.matcher(phoneNum.getText()).matches() || phoneNum.getText().isEmpty()) {
                isValid = false;
                phoneNumValidLabel.setVisible(true);
            } else {
                phoneNumValidLabel.setVisible(false);
            }
        }

        // Check bdate field
        if (selectedUser instanceof Client) {
            if (bDate.getValue() == null) {
                isValid = false;
                bDateFieldCheck.setVisible(true);
            } else {
                bDateFieldCheck.setVisible(false);
            }
        }
        // Check if all fields are not empty
        if (FxUtils.areAllFieldsNotEmpty(usersManagePane) || !isValid) {
            isValid = false;
            invalidInputLabel.setVisible(true);
        } else {
            invalidInputLabel.setVisible(false);
        }

        return isValid;
    }

    public boolean checkIfUserSelected() {
        boolean userSelected = true;
        if (selectedUser == null) {
            userSelected = false;
            noSelectionLabel.setVisible(true);
            return userSelected;
        } else {
            noSelectionLabel.setVisible(false);
        }
        return userSelected;
    }

    // END OF USER TAB

    // PUBLICATION TAB

    public void openAddWindow() throws IOException {
        if (!publicationTypeComboBox.getSelectionModel().isEmpty()) {
            typeNotSelectedWarningLabel.setVisible(false);
            DataTransfer info = DataTransfer.getInstance();
            info.setAddWasPressed(true);
            info.setText(publicationTypeComboBox.getValue());
            StartGUI.newStage("/org.books/productInfo.fxml", "Product View");
            dataPopulator.fillTableWithRecordsByCriteria(publicationsListField, Publication.class, "dtype", publicationTypeComboBox.getValue());
        } else {
            typeNotSelectedWarningLabel.setVisible(true);
        }
    }

    public void loadPublicationList() {
        dataPopulator.fillTableWithRecordsByCriteria(publicationsListField, Publication.class, "dtype", publicationTypeComboBox.getValue());
    }

    // END OF PUBLICATION TAB
}
