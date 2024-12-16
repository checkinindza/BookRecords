package org.books.fxControllers;

//<editor-fold desc="import zone">
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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
import org.books.utils.PasswordUtils;

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

    //<editor-fold desc="Admin publications table">
    @FXML
    public TableView<AdminPublicationsTableParameters> adminPublicationsTable;
    @FXML
    public TableColumn<AdminPublicationsTableParameters, Integer> colAdminPublicationsId;
    @FXML
    public TableColumn<AdminPublicationsTableParameters, String> colAdminPublicationsTitle;
    @FXML
    public TableColumn colAdminPublicationsStatus;
    @FXML
    public TableColumn colAdminPublicationsOwner;
    @FXML
    public TableColumn colAdminPublicationsBorrower;
    @FXML
    public TableColumn colAdminPublicationsDummy;
    @FXML
    public AnchorPane publicationsPane;
    @FXML
    public ListView<Object> publicationsListField;
    @FXML
    public ComboBox<String> publicationTypeComboBox;
    @FXML
    public Label typeNotSelectedWarningLabel;
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
    @FXML
    public ComboBox<String> userBooksPublicationTypeComboBox;
    @FXML
    public Label userTabTypeNotSelectedWarningLabel;
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
                private boolean isUpdating = false;
                private final ChoiceBox<PublicationStatus> bookStatus = new ChoiceBox<>();
                {
                    bookStatus.getItems().addAll(PublicationStatus.values());
                    bookStatus.setOnAction(event -> {
                        if (!isUpdating) {
                            BookTableParameters rowData = getTableRow().getItem();
                            if (rowData != null) {
                                rowData.setPublicationStatus(bookStatus.getValue());

                                Publication publication = hibernate.getEntityById(Publication.class, rowData.getId());
                                publication.setPublicationStatus(bookStatus.getValue());
                                hibernate.update(publication);

                                insertPublicationRecord(publication);
                            }
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        isUpdating = true;
                        BookTableParameters rowData = getTableRow().getItem();
                        bookStatus.setValue(rowData.getPublicationStatus());
                        setGraphic(bookStatus);
                        isUpdating = false;
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
                        StartGUI.newStage("/org.books/productInfo.fxml", "Edit publication");
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
        //<editor-fold desc="Admin publications table initialize">
        colAdminPublicationsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAdminPublicationsTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        Callback<TableColumn<AdminPublicationsTableParameters, Void>, TableCell<AdminPublicationsTableParameters, Void>> callBackAdminPublicationStatus = param -> {
            final TableCell<AdminPublicationsTableParameters, Void> cell = new TableCell<>() {
                private boolean isUpdating = false;
                private final ChoiceBox<PublicationStatus> publicationStatus = new ChoiceBox<>();
                {
                    publicationStatus.getItems().addAll(PublicationStatus.values());
                    publicationStatus.setOnAction(event -> {
                        if (!isUpdating) {
                            AdminPublicationsTableParameters rowData = getTableRow().getItem();
                            if (rowData != null) {
                                rowData.setPublicationStatus(publicationStatus.getValue());

                                Publication publication = hibernate.getEntityById(Publication.class, rowData.getId());
                                publication.setPublicationStatus(publicationStatus.getValue());
                                hibernate.update(publication);

                                insertPublicationRecord(publication);
                            }
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        isUpdating = true;
                        AdminPublicationsTableParameters rowData = getTableRow().getItem();
                        publicationStatus.setValue(rowData.getPublicationStatus());
                        setGraphic(publicationStatus);
                        isUpdating = false;
                    }
                }
            };
            return cell;
        };
        colAdminPublicationsStatus.setCellFactory(callBackAdminPublicationStatus);
        Callback<TableColumn<AdminPublicationsTableParameters, Void>, TableCell<AdminPublicationsTableParameters, Void>> callBackAdminPublicationOwner = param -> {
            final TableCell<AdminPublicationsTableParameters, Void> cell = new TableCell<>() {
                private boolean isUpdating = false;
                private final ChoiceBox<Client> publicationOwner = new ChoiceBox<>();
                {
                    publicationOwner.getItems().addAll(hibernate.getAllRecords(Client.class));
                    publicationOwner.setOnAction(event -> {
                        if (!isUpdating) {
                            AdminPublicationsTableParameters rowData = getTableRow().getItem();
                            if (rowData != null) {
                                Publication publication = hibernate.getEntityById(Publication.class, rowData.getId());

                                rowData.setPublicationOwner(publicationOwner.getValue().getName() + " " + publicationOwner.getValue().getSurname());


                                publication.setOwner(publicationOwner.getValue());
                                hibernate.update(publication);

                                insertPublicationRecord(publication);
                            }
                        }
                    });
                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        isUpdating = true;
                        publicationOwner.getItems().clear();
                        publicationOwner.getItems().addAll(hibernate.getAllRecords(Client.class));
                        AdminPublicationsTableParameters rowData = getTableRow().getItem();
                        Client client = (Client) hibernate.getUserByPublication(rowData.getId());
                        if (client != null) {
                            publicationOwner.setValue(client);
                        } else {
                            publicationOwner.setValue(null);
                        }
                        setGraphic(publicationOwner);
                        isUpdating = false;
                    }
                }
            };
            return cell;
        };
        colAdminPublicationsOwner.setCellFactory(callBackAdminPublicationOwner);
        Callback<TableColumn<AdminPublicationsTableParameters, Void>, TableCell<AdminPublicationsTableParameters, Void>> callbackAdminPublicationsDeleteButton = param -> {
            final TableCell<AdminPublicationsTableParameters, Void> cell = new TableCell<>() {
                private final Button deleteButton = new Button("Delete"); {
                    deleteButton.setOnAction(event -> {
                        AdminPublicationsTableParameters row = getTableView().getItems().get(getIndex());
                        if (row.getPublicationStatus() == PublicationStatus.AVAILABLE) {
                            hibernate.deletePublication(row.getId());
                            fillAdminPublicationsTablePerSelectedType();
                            return;
                        } else {
                            FxUtils.generateAlertWithoutHeader(Alert.AlertType.ERROR, "Error", "You cannot delete a book that is not available");
                        }
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

        colAdminPublicationsDummy.setCellFactory(callbackAdminPublicationsDeleteButton);

        adminPublicationsTable.setRowFactory(tv -> {
            TableRow<AdminPublicationsTableParameters> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        AdminPublicationsTableParameters rowData = row.getItem();
                        dataTransfer.setObject(hibernate.getEntityById(Publication.class, rowData.getId()));
                        dataTransfer.setUpdateWasPressed(true);
                        dataTransfer.setEntityManagerFactory(entityManagerFactory);
                        StartGUI.newStage("/org.books/productInfo.fxml", "Edit publication");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
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
        dataPopulator.fillComboBox(userBooksPublicationTypeComboBox, dataPopulator.getChildren(Publication.class));

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
            if (publication.getBorrowerClient() != null) {
                bookTableParameters.setPublicationUser(publication.getBorrowerClient().getName() + " " + publication.getBorrowerClient().getSurname());
            }
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

    public void fillAdminPublicationsTablePerSelectedType() {
        adminPublicationsTable.getItems().clear();
        List<Publication> publications = hibernate.getRecordsByCriteria(Publication.class, "dtype", publicationTypeComboBox.getValue());
        for (Publication publication : publications) {
            AdminPublicationsTableParameters adminPublicationsTableParameters = new AdminPublicationsTableParameters();
            adminPublicationsTableParameters.setId(publication.getId());
            adminPublicationsTableParameters.setTitle(publication.getTitle());
            adminPublicationsTableParameters.setPublicationStatus(publication.getPublicationStatus());
            if (publication.getOwner() != null) adminPublicationsTableParameters.setPublicationOwner(publication.getOwner().getName() + " " + publication.getOwner().getSurname());
            if (publication.getBorrowerClient() != null) adminPublicationsTableParameters.setPublicationBorrower(publication.getBorrowerClient().getName() + " " + publication.getBorrowerClient().getSurname());
            adminPublicationsTable.getItems().add(adminPublicationsTableParameters);
        }
    }
    //</editor-fold>

    // USER TAB

    public void createNewUser() {
        if (!isInputValid()) {
            return;
        } else {
            if (clientChk.isSelected()) {
                Client client = new Client(loginField.getText(), PasswordUtils.hashPassword(pswField.getText()), nameField.getText(), surnameField.getText(), emailField.getText(), bDate.getValue(), addressField.getText());
                hibernate.create(client);
            } else {
                Admin admin = new Admin(loginField.getText(), PasswordUtils.hashPassword(pswField.getText()), nameField.getText(), surnameField.getText(), emailField.getText(), phoneNum.getText());
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
                    if (!pswField.getText().isEmpty()) {
                        client.setPassword(PasswordUtils.hashPassword(pswField.getText()));
                    }
                    client.setEmail(emailField.getText());
                    hibernate.update(client);
                } else {
                    Admin admin = hibernate.getEntityById(Admin.class, selectedUser.getId());
                    admin.setName(nameField.getText());
                    admin.setSurname(surnameField.getText());
                    admin.setLogin(loginField.getText());
                    if (!pswField.getText().isEmpty()) {
                        admin.setPassword(PasswordUtils.hashPassword(pswField.getText()));
                    }
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
        if (adminChk.isSelected()) {
            Pattern phoneNumPattern = Pattern.compile("\\+[0-9]{11}");
            if (!phoneNumPattern.matcher(phoneNum.getText()).matches() || phoneNum.getText().isEmpty()) {
                isValid = false;
                phoneNumValidLabel.setVisible(true);
            } else {
                phoneNumValidLabel.setVisible(false);
            }
        }

        // Check bdate field
        if (clientChk.isSelected()) {
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

    public void openProductEditWindow() throws IOException {
        if (publicationManagementTab.isSelected()) {
            loadProductEditWindow(typeNotSelectedWarningLabel, publicationTypeComboBox);
            fillAdminPublicationsTablePerSelectedType();
        } else if (clientBookManagementTab.isSelected()) {
            loadProductEditWindow(userTabTypeNotSelectedWarningLabel, userBooksPublicationTypeComboBox);
            fillBookList();
        }
    }

    public void loadProductEditWindow(Label label, ComboBox<String> comboBox) throws IOException {
        if (!comboBox.getSelectionModel().isEmpty()) {
            label.setVisible(false);
            dataTransfer.setAddWasPressed(true);
            dataTransfer.setData(comboBox.getValue());
            dataTransfer.setEntityManagerFactory(entityManagerFactory);
            dataTransfer.setUser(currentUser);
            StartGUI.newStage("/org.books/productInfo.fxml", "Add publication");
        } else {
            label.setVisible(true);
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
        } else if (publicationManagementTab.isSelected()) {
            fillAdminPublicationsTablePerSelectedType();
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
        PeriodicRecord periodicRecord = new PeriodicRecord(currentUser, publication, LocalDate.now(), publication.getPublicationStatus());
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

    public void deleteUserPublication() {
        BookTableParameters row = myBookList.getSelectionModel().getSelectedItem();
        if (row.getPublicationStatus() == PublicationStatus.AVAILABLE) {
            hibernate.deletePublication(row.getId());
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.INFORMATION, "Success", "Publication deleted successfully");
            fillBookList();
        } else {
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.ERROR, "Error", "You cannot delete a book that is not available");
        }
    }
    // END OF PUBLICATION TAB
}
