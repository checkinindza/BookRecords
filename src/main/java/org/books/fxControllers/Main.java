package org.books.fxControllers;

//<editor-fold desc="import zone">
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.books.Model.*;
import org.books.Model.enums.PublicationStatus;
import org.books.StartGUI;
import org.books.hibernateControllers.CustomHibernate;
import org.books.utils.DataPopulator;
import org.books.utils.DataTransfer;
import org.books.utils.FxUtils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
//</editor-fold>

public class Main implements Initializable {

    @FXML
    public TabPane allTabsPane;

    //<editor-fold desc="User management tabs fields">
    @FXML
    public Tab originalUserManagementTab;
    @FXML
    public Tab userManagementTab;
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
    //</editor-fold>

    //<editor-fold desc="Main publication tab fields">
    @FXML
    public AnchorPane publicationsPane;
    @FXML
    public ListView<Object> publicationsListField;
    @FXML
    public ComboBox<String> publicationTypeComboBox;
    @FXML
    public Label typeNotSelectedWarningLabel;
    @FXML
    public Label publicationNotSelectedWarningLabel;
    //</editor-fold>

    //<editor-fold desc="Alternative user management tab fields">
    @FXML
    public TableView userTable;
    @FXML
    public TableColumn<UserTableParameters, Integer> colId;
    @FXML
    public TableColumn<UserTableParameters, String> colUsername;
    @FXML
    public TableColumn<UserTableParameters, String> colPassword;
    @FXML
    public TableColumn<UserTableParameters, String> colName;
    @FXML
    public TableColumn<UserTableParameters, String> colSurname;
    @FXML
    public TableColumn<UserTableParameters, String> colAddress;
    @FXML
    public TableColumn<UserTableParameters, String> colPhoneNum;
    @FXML
    public TableColumn<UserTableParameters, String> colEmail;
    @FXML
    public TableColumn<UserTableParameters, LocalDate> colUserBirthDate;
    @FXML
    public TableColumn colDummy;
    //</editor-fold>

    //<editor-fold desc="Main exchange tab fields">
    @FXML
    public Tab bookExchangeTab;
    @FXML
    public ListView<Publication> availableBooksList;
    @FXML
    public TextArea aboutBookTextArea;
    @FXML
    public TextArea ownerBioTextArea;
    @FXML
    public Label owerInfoLabel;
    @FXML
    public ListView<Comment> chatList;
    @FXML
    public TextArea messageTextArea;
    @FXML
    public Tab publicationManagementTab;

    //</editor-fold>

    //<editor-fold desc="Main client books tab fields">
    //<editor-fold desc="Client's books table">
    @FXML
    public Tab clientBookManagementTab;
    @FXML
    public TableView<BookTableParameters> myBookList;
    @FXML
    public TableColumn<BookTableParameters, String> colBookTitle;
    @FXML
    public TableColumn<BookTableParameters, String> colBookUser;
    @FXML
    public TableColumn colBookStatus;
    @FXML
    public TableColumn<BookTableParameters, Integer> colRequestDate;
    @FXML
    public TableColumn<BookTableParameters, Integer> colBookId;
    @FXML
    public TableColumn colBooksDummy;
    //</editor-fold>
    //<editor-fold desc="Borrowed books table">
    @FXML
    public TableView<BorrowedBooksTableParameters> borrowedBooksTable;
    @FXML
    public TableColumn<BorrowedBooksTableParameters, Integer> colBorrowedBookID;
    @FXML
    public TableColumn<BorrowedBooksTableParameters, String> colBorrowedBookTitle;
    @FXML
    public TableColumn<BorrowedBooksTableParameters, LocalDate> colBorrowedBookBorrowDate;
    @FXML
    public TableColumn colReturnButton;
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Other variables">
    private User currentUser;
    private User selectedUser = null;
    private Publication selectedPublication = null;

    EntityManagerFactory entityManagerFactory;
    private CustomHibernate hibernate;
    private DataPopulator dataPopulator = new DataPopulator();
    //</editor-fold>

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //<editor-fold desc="User management tab initialize">
        userTable.setEditable(true);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("userAddress"));
        colPhoneNum.setCellValueFactory(new PropertyValueFactory<>("userPhone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        colUserBirthDate.setCellValueFactory(new PropertyValueFactory<>("userBirthDate"));

        //<editor-fold desc="Edit cell per user type">
        colPhoneNum.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setUserPhone(event.getNewValue());
            User user = hibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            if (user instanceof Admin admin) {
                admin.setPhone(event.getNewValue());
                hibernate.update(admin);
            }
        });

        colAddress.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setUserAddress(event.getNewValue());
            User user = hibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            if (user instanceof Client client) {
                client.setAddress(event.getNewValue());
                hibernate.update(client);
            }
        });

        colUserBirthDate.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setUserBirthDate(event.getNewValue());
            User user = hibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            if (user instanceof Client client) {
                client.setBirthDate(event.getNewValue());
                hibernate.update(client);
            }
        });
        //</editor-fold>

        Callback<TableColumn<UserTableParameters, Void>, TableCell<UserTableParameters, Void>> callback = param -> {
            final TableCell<UserTableParameters, Void> cell = new TableCell<>() {
                private final Button deleteButton = new Button("Delete");
                {
                    deleteButton.setOnAction(event -> {
                        UserTableParameters row = getTableView().getItems().get(getIndex());
                        hibernate.delete(User.class, row.getId());
                        fillUserTable();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        };
        colDummy.setCellFactory(callback);

        //</editor-fold>
        //<editor-fold desc="My Books table initialize">
        colBookId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("publicationTitle"));
        colBookUser.setCellValueFactory(new PropertyValueFactory<>("publicationUser"));
        colRequestDate.setCellValueFactory(new PropertyValueFactory<>("publicationRequestDate"));

        Callback<TableColumn<BookTableParameters, Void>, TableCell<BookTableParameters, Void>> callbackBookStatus = param -> {
            final TableCell<BookTableParameters, Void> cell = new TableCell<>() {
                private final ChoiceBox<PublicationStatus> bookStatus = new ChoiceBox<>();
                {
                    bookStatus.getItems().addAll(PublicationStatus.values());
                    bookStatus.setOnAction(event -> {
                        BookTableParameters rowData = getTableRow().getItem();
                        if (rowData != null) {
                            rowData.setPublicationStatus(bookStatus.getValue());

                            Publication publication = hibernate.getEntityById(Publication.class, rowData.getId());
                            publication.setPublicationStatus(bookStatus.getValue());
                            hibernate.update(publication);

                            insertPublicationRecord(publication);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        BookTableParameters rowData = getTableRow().getItem();
                        bookStatus.setValue(rowData.getPublicationStatus());
                        setGraphic(bookStatus);
                    }
                }
            };
            return cell;
        };
        colBookStatus.setCellFactory(callbackBookStatus);

        myBookList.setRowFactory(tv -> {
            TableRow<BookTableParameters> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        BookTableParameters rowData = row.getItem();
                        dataTransfer.setObject(hibernate.getEntityById(Publication.class, rowData.getId()));
                        dataTransfer.setUpdateWasPressed(true);
                        dataTransfer.setEntityManagerFactory(entityManagerFactory);
                        StartGUI.newStage("/org.books/productInfo.fxml", "Product View");
                        fillBookList();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        Callback<TableColumn<BookTableParameters, Void>, TableCell<BookTableParameters, Void>> historyButton = param -> new TableCell<>() {
            private final Button viewHistoryBtn = new Button("View history");

            {
                viewHistoryBtn.setOnAction(event -> {
                    BookTableParameters row = getTableView().getItems().get(getIndex());
                    try {
                        loadHistory(row.getId(), currentUser);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewHistoryBtn);
                }
            }
        };

        colBooksDummy.setCellFactory(historyButton);
        //</editor-fold>
        //<editor-fold desc="Borrowed books table initialize">
        borrowedBooksTable.setEditable(true);
        colBorrowedBookID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBorrowedBookTitle.setCellValueFactory(new PropertyValueFactory<>("publicationTitle"));
        colBorrowedBookBorrowDate.setCellValueFactory(new PropertyValueFactory<>("publicationBorrowDate"));

        Callback<TableColumn<BorrowedBooksTableParameters, Void>, TableCell<BorrowedBooksTableParameters, Void>> callbackReturnButton = param -> {
            final TableCell<BorrowedBooksTableParameters, Void> cell = new TableCell<>() {
                private final Button returnButton = new Button("Return");
                {
                    returnButton.setOnAction(event -> {
                        BorrowedBooksTableParameters row = getTableView().getItems().get(getIndex());
                        Publication publication = hibernate.getEntityById(Publication.class, row.getId());
                        publication.setBorrowerClient(null);
                        publication.setPublicationStatus(PublicationStatus.AVAILABLE);
                        hibernate.update(publication);

                        insertPublicationRecord(publication);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(returnButton);
                    }
                }
            };
            return cell;
        };
        colReturnButton.setCellFactory(callbackReturnButton);
        //</editor-fold>
    }

    private void loadHistory(int id, User currentUser) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/org.books/history.fxml"));
        Parent parent = fxmlLoader.load();
        History history = fxmlLoader.getController();
        history.setData(entityManagerFactory, currentUser, id);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("History");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.hibernate = new CustomHibernate(entityManagerFactory);
        dataPopulator.fillTableWithAllRecords(userListField, User.class);
        dataPopulator.fillComboBox(publicationTypeComboBox, dataPopulator.getChildren(Publication.class));

        // Priklausomai nuo prisijungusuio, apribojam matomumą

        enableVisibility();
    }

    private void enableVisibility() {
        if (currentUser instanceof Client) {
            allTabsPane.getTabs().remove(userManagementTab);
            allTabsPane.getTabs().remove(publicationManagementTab);
            allTabsPane.getTabs().remove(originalUserManagementTab);
        } else {
            allTabsPane.getTabs().remove(clientBookManagementTab);
        }
    }

    //<editor-fold desc="Fill tables with data">
    private void fillUserTable() {
        userTable.getItems().clear();
        List<User> allUsers = hibernate.getAllRecords(User.class);
        for (User u : allUsers) {
            UserTableParameters userTableParameters = new UserTableParameters();
            userTableParameters.setId(u.getId());
            userTableParameters.setUsername(u.getLogin());
            userTableParameters.setPassword(u.getPassword());
            userTableParameters.setName(u.getName());
            userTableParameters.setSurname(u.getSurname());
            if (u instanceof Client client) {
                userTableParameters.setUserAddress(client.getAddress());
                userTableParameters.setUserBirthDate(client.getBirthDate());
            }
            userTableParameters.setUserEmail(u.getEmail());
            if (u instanceof Admin admin) {
                userTableParameters.setUserPhone(admin.getPhoneNum());
            }
            userTable.getItems().add(userTableParameters);
        }
    }

    private void fillBookList() {
        myBookList.getItems().clear();
        List<Publication> allPublications = hibernate.getUserPublications(currentUser);
        for (Publication publication : allPublications) {
            BookTableParameters bookTableParameters = new BookTableParameters();
            bookTableParameters.setId(publication.getId());
            bookTableParameters.setPublicationTitle(publication.getTitle());
            bookTableParameters.setPublicationUser(publication.getOwner().getName() + " " + publication.getOwner().getSurname());
            bookTableParameters.setPublicationRequestDate(publication.getRequestDate());
            bookTableParameters.setPublicationStatus(publication.getPublicationStatus());
            myBookList.getItems().add(bookTableParameters);
        }
    }

    private void fillBorrowedBooksTable() {
        borrowedBooksTable.getItems().clear();
        List<Publication> allPublications = hibernate.getBorrowedPublications(currentUser);
        for (Publication publication : allPublications) {
            BorrowedBooksTableParameters borrowedBooksTableParameters = new BorrowedBooksTableParameters();
            borrowedBooksTableParameters.setId(publication.getId());
            borrowedBooksTableParameters.setPublicationTitle(publication.getTitle());
            borrowedBooksTable.getItems().add(borrowedBooksTableParameters);
        }
    }
    //</editor-fold>

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
        selectedUser = userListField.getSelectionModel().getSelectedItem();
        User latestUser = hibernate.getEntityById(User.class, selectedUser.getId());
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
        if (!checkIfSelectionWasMade(usersManagePane, "noSelectionLabel", selectedUser)) {
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
        if (!checkIfSelectionWasMade(usersManagePane, "noSelectionLabel", selectedUser)) {
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

    public boolean checkIfSelectionWasMade(Pane parent, String labelChildFxId, Object object) {
        boolean selectionMade = true;
        if (object == null) {
            selectionMade = false;
            parent.lookup("#" + labelChildFxId).setVisible(true);
            return selectionMade;
        } else {
            parent.lookup("#" + labelChildFxId).setVisible(false);
        }
        return selectionMade;
    }

    // END OF USER TAB

    // PUBLICATION TAB

    DataTransfer dataTransfer = DataTransfer.getInstance();

    public void openAddWindow() throws IOException {
        if (!publicationTypeComboBox.getSelectionModel().isEmpty()) {
            typeNotSelectedWarningLabel.setVisible(false);
            dataTransfer.setAddWasPressed(true);
            dataTransfer.setText(publicationTypeComboBox.getValue());
            dataTransfer.setEntityManagerFactory(entityManagerFactory);
            StartGUI.newStage("/org.books/productInfo.fxml", "Product View");
            dataPopulator.fillTableWithRecordsByCriteria(publicationsListField, Publication.class, "dtype", publicationTypeComboBox.getValue());
        } else {
            typeNotSelectedWarningLabel.setVisible(true);
        }
    }

    public void loadPublicationList() {
        dataPopulator.fillTableWithRecordsByCriteria(publicationsListField, Publication.class, "dtype", publicationTypeComboBox.getValue());
    }

    public void deletePublication() {
        selectedPublication = (Publication) publicationsListField.getSelectionModel().getSelectedItem();
        if (!checkIfSelectionWasMade(publicationsPane, "publicationNotSelectedWarningLabel", selectedPublication)) {
            return;
        } else {
            hibernate.delete(Publication.class, selectedPublication.getId());
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.INFORMATION, "Success", "Publication deleted successfully");
            dataPopulator.fillTableWithRecordsByCriteria(publicationsListField, Publication.class, "dtype", publicationTypeComboBox.getValue());}
    }

    public void updatePublication() throws IOException {
        if (!publicationTypeComboBox.getValue().isEmpty()) {
            selectedPublication = (Publication) publicationsListField.getSelectionModel().getSelectedItem();
            if (!checkIfSelectionWasMade(publicationsPane, "publicationNotSelectedWarningLabel", selectedPublication)) {
                return;
            } else {
                typeNotSelectedWarningLabel.setVisible(false);
                dataTransfer.setUpdateWasPressed(true);
                dataTransfer.setObject(selectedPublication);
                dataTransfer.setText(publicationTypeComboBox.getValue());
                dataTransfer.setEntityManagerFactory(entityManagerFactory);
                StartGUI.newStage("/org.books/productInfo.fxml", "Product View");
                dataPopulator.fillTableWithRecordsByCriteria(publicationsListField, Publication.class, "dtype", publicationTypeComboBox.getValue());
            }
        } else {
            typeNotSelectedWarningLabel.setVisible(true);
        }
    }

    public void sendMessage() {
    }

    public void loadData() {
        if (userManagementTab.isSelected()) {
            fillUserTable();
        } else if (bookExchangeTab.isSelected()) {
            availableBooksList.getItems().clear();
            availableBooksList.getItems().addAll(hibernate.getAvailablePublications(currentUser));
        } else if (clientBookManagementTab.isSelected()) {
            fillBookList();
            fillBorrowedBooksTable();
        }
    }

    public void loadPublicationInfo() {
        Publication publication = availableBooksList.getSelectionModel().getSelectedItem();
        Publication publicationFromDb = hibernate.getEntityById(Publication.class, publication.getId());
        aboutBookTextArea.setText(
                "Title: " + publicationFromDb.getTitle() + "\n" +
                "Year: " + publicationFromDb.getPublicationDate());
        owerInfoLabel.setText(publicationFromDb.getOwner().getName() + " " + publicationFromDb.getOwner().getSurname());
        ownerBioTextArea.setText(publicationFromDb.getOwner().getClientBio());
    }

    public void loadReviewWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/org.books/userReview.fxml"));
        Parent parent = fxmlLoader.load();
        UserReview userReview = fxmlLoader.getController();
        userReview.setData(entityManagerFactory, currentUser, availableBooksList.getSelectionModel().getSelectedItem().getOwner());
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Book exchange");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void insertPublicationRecord(Publication publication) {
        PeriodicRecord periodicRecord = new PeriodicRecord(publication.getBorrowerClient(), publication, LocalDate.now(), publication.getPublicationStatus());
        hibernate.create(periodicRecord);
    }

    public void reserveBook() {
        Publication publication = availableBooksList.getSelectionModel().getSelectedItem();
        Publication publicationFromDb = hibernate.getEntityById(Publication.class, publication.getId());
        publicationFromDb.setPublicationStatus(PublicationStatus.REQUESTED);
        publicationFromDb.setBorrowerClient((Client) currentUser);
        hibernate.update(publicationFromDb);

        PeriodicRecord periodicRecord = new PeriodicRecord((Client) currentUser, publicationFromDb, LocalDate.now(), PublicationStatus.REQUESTED);
        hibernate.create(periodicRecord);
    }
    // END OF PUBLICATION TAB
}
