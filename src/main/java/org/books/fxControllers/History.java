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
import org.books.utils.FxUtils;

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
        FxUtils.setComboBoxCellFactory(publicationStatusField);
        publicationStatusField.getItems().addAll(PublicationStatus.values());
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
        filteredData = new FilteredList<>(tableData, p -> true);
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
            if (borrowerComboBox.getValue() != null) {
                if(record.getBorrowerClient() == null || record.getBorrowerClient().getId() != borrowerComboBox.getSelectionModel().getSelectedItem().getId()) {
                    System.out.println(record.getBorrowerClient() + " " + borrowerComboBox.getValue());
                    return false;
                }
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
        FxUtils.setComboBoxCellFactory(borrowerComboBox);
        borrowerComboBox.getItems().addAll(hibernate.getAllRecords(Client.class));
    }
}