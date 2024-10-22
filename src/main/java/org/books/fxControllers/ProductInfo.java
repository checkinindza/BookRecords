package org.books.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.books.Model.Manga;
import org.books.Model.enums.Demographic;
import org.books.Model.enums.Format;
import org.books.Model.enums.Frequency;
import org.books.Model.enums.Language;
import org.books.StartGUI;
import org.books.hibernateControllers.GenericHibernate;
import org.books.utils.DataPopulator;
import org.books.utils.DataTransfer;
import org.books.utils.FxUtils;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class ProductInfo implements Initializable {
    @FXML
    public Label itemNameLabel;
    @FXML
    public Label addPublicationLabel;
    @FXML
    public TextField titleField;
    @FXML
    public Spinner<Integer> pageCountSpinner;
    @FXML
    public TextField identificationNumberField;
    @FXML
    public ComboBox<Language> languageComboBox;
    @FXML
    public ComboBox<Format> formatComboBox;
    @FXML
    public ComboBox<Demographic> demographicComboBox;
    @FXML
    public CheckBox colorizedCheckBox;
    @FXML
    public ComboBox<Frequency> frequencyComboBox;
    @FXML
    public TextField issueNumberField;
    @FXML
    public TextField editorField;
    @FXML
    public AnchorPane productInfoPane;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField volumeNumberField;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    GenericHibernate hibernate = new GenericHibernate(entityManagerFactory);
    DataPopulator dataPopulator = new DataPopulator();
    DataTransfer dataTransfer = DataTransfer.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAddPublicationLabel();
        setupSpinner();
        fillComboBoxes();
        disableFields();
    }

    public void setupSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE,1);
        pageCountSpinner.setTooltip(new Tooltip("Number of pages"));
        pageCountSpinner.setValueFactory(valueFactory);
        TextFormatter<Integer> textFormatter = new TextFormatter<>(c -> {
            if (c.getControlNewText().matches("\\d*")) {
                return c;
            } else {
                return null;
            }
        });
        pageCountSpinner.getEditor().setTextFormatter(textFormatter);
    }

    public void fillComboBoxes() {
        dataPopulator.fillComboBoxWithEnums(Language.class, languageComboBox);
        dataPopulator.fillComboBoxWithEnums(Format.class, formatComboBox);
        dataPopulator.fillComboBoxWithEnums(Demographic.class, demographicComboBox);
        dataPopulator.fillComboBoxWithEnums(Frequency.class, frequencyComboBox);
    }

    public void disableFields() {
        if (dataTransfer.getText().equals("Manga")) {
            issueNumberField.setDisable(true);
            editorField.setDisable(true);
            formatComboBox.setDisable(true);
            frequencyComboBox.setDisable(true);
        }
    }

    public void createNewPublication(ActionEvent actionEvent) {
        if (!isInputEmpty()) {
            return;
        }

        if (dataTransfer.getText().equals("Manga")) {
            Manga manga = new Manga(titleField.getText(), languageComboBox.getValue(), datePicker.getValue(), pageCountSpinner.getValue(), editorField.getText(), identificationNumberField.getText(), "Summary", Integer.parseInt(identificationNumberField.getText()), "Illustrator", Integer.parseInt(volumeNumberField.getText()), demographicComboBox.getValue(), null, colorizedCheckBox.isSelected());
            hibernate.create(manga);
        }

        FxUtils.generateAlertWithoutHeader(Alert.AlertType.INFORMATION, "Success", "Publication created successfully");
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setAddPublicationLabel() {
        if (dataTransfer.getAddWasPressed()) {
            String title = addPublicationLabel.getText();
            addPublicationLabel.setText(title + " " + dataTransfer.getText());
            Text text = new Text(addPublicationLabel.getText());
            text.setFont(addPublicationLabel.getFont());
            text.setStyle(addPublicationLabel.getStyle());
            double width = text.getLayoutBounds().getWidth();
            addPublicationLabel.setMinWidth(width);
            addPublicationLabel.setVisible(true);
        }
    }

    public boolean isInputEmpty() {
        boolean isEmpty = false;
        
        if (FxUtils.areAllFieldsNotEmpty(productInfoPane)) isEmpty = true;

        if (dataTransfer.getText().equals("Manga")) {
            if (languageComboBox.getValue() == null || demographicComboBox.getValue() == null || !colorizedCheckBox.isSelected()) {
                isEmpty = true;
            }
        }
        
        FxUtils.generateAlertWithoutHeader(Alert.AlertType.ERROR, "Error", "You missed something!");
        return isEmpty;
    }

    public void openGenreChooser() throws IOException {
        StartGUI.newStage("/org.books/genreChooser.fxml", "Genre Chooser");
    }

    public void exitProductInfo(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
