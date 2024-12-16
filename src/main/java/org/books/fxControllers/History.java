package org.books.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.books.Model.Client;
import org.books.Model.PeriodicRecord;
import org.books.Model.User;
import org.books.Model.enums.PublicationStatus;
import org.books.hibernateControllers.CustomHibernate;

import java.net.URL;
import java.util.ResourceBundle;

public class History implements Initializable {

    @FXML
    public TableView<PeriodicRecord> bookHistoryTable;
    @FXML
    public TableColumn<PeriodicRecord, Integer> colId;
    @FXML
    public TableColumn<PeriodicRecord, String> colBorrower;
    @FXML
    public TableColumn<PeriodicRecord, String> colStatus;
    @FXML
    public TableColumn<PeriodicRecord, String> colTransactionDate;
    @FXML
    public TableColumn<PeriodicRecord, String> colPublication;
    @FXML
    public TableColumn<PeriodicRecord, String> colClient;
    @FXML
    public DatePicker startDateField;
    @FXML
    public DatePicker endDateField;
    @FXML
    public ComboBox<PublicationStatus> publicationStatusField;
    @FXML
    public ComboBox<Client> borrowerComboBox;

    private final ObservableList<PeriodicRecord> tableData = FXCollections.observableArrayList();

    private FilteredList<PeriodicRecord> filteredData;

    EntityManagerFactory entityManagerFactory;
    CustomHibernate hibernate;
    User currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filteredData = new FilteredList<>(tableData, p -> true);
        publicationStatusField.getItems().add(null);
        publicationStatusField.getItems().addAll(PublicationStatus.values());
        //<editor-fold desc="For null entry">
        publicationStatusField.setCellFactory(comboBox -> new ListCell<PublicationStatus>() {
            @Override
            protected void updateItem(PublicationStatus item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("None");
                } else {
                    setText(item.toString());
                }
            }
        });
        //</editor-fold>
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTransactionDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        colPublication.setCellValueFactory(new PropertyValueFactory<>("publication"));
        colClient.setCellValueFactory(new PropertyValueFactory<>("actionUser"));
        colBorrower.setCellValueFactory(new PropertyValueFactory<>("borrowerClient"));
    }

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser) {
        this.entityManagerFactory = entityManagerFactory;
        this.hibernate = new CustomHibernate(entityManagerFactory);
        this.currentUser = currentUser;
        loadAllRecords();
    }

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, int id) {
        this.entityManagerFactory = entityManagerFactory;
        this.hibernate = new CustomHibernate(entityManagerFactory);
        this.currentUser = currentUser;
        loadPublicationsById(id);
        setupBorrowerCombobox();
    }

    private void loadAllRecords() {
        bookHistoryTable.getItems().clear();
        bookHistoryTable.getItems().addAll(hibernate.getAllRecords(PeriodicRecord.class));
    }

    private void loadPublicationsById(int id) {
        bookHistoryTable.getItems().clear();
        tableData.addAll(hibernate.getPeriodicById(id));
        bookHistoryTable.getItems().addAll(tableData);
    }

    public void filterRecords() {
        filteredData.setPredicate(record -> {
            if (startDateField.getValue() != null && record.getTransactionDate().isBefore(startDateField.getValue())) {
                return false;
            }
            if (endDateField.getValue() != null && record.getTransactionDate().isAfter(endDateField.getValue())) {
                return false;
            }
            if (publicationStatusField.getValue() != null && !record.getStatus().equals(publicationStatusField.getValue())) {
                return false;
            }
            if (borrowerComboBox.getValue() != null && !record.getBorrowerClient().equals(borrowerComboBox.getValue())) {
                return false;
            }
            return true;
        });
        SortedList<PeriodicRecord> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(bookHistoryTable.comparatorProperty());
        bookHistoryTable.getItems().clear();
        bookHistoryTable.getItems().addAll(sortedData);
    }

    private void setupBorrowerCombobox() {
        borrowerComboBox.getItems().clear();
        borrowerComboBox.getItems().addAll(hibernate.getAllRecords(Client.class));
    }
}