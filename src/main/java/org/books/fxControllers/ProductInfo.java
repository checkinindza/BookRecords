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
import org.books.Model.Book;
import org.books.Model.Manga;
import org.books.Model.Periodical;
import org.books.Model.enums.*;
import org.books.StartGUI;
import org.books.hibernateControllers.GenericHibernate;
import org.books.utils.DataPopulator;
import org.books.utils.DataTransfer;
import org.books.utils.FxUtils;
import org.books.utils.SelectedGenresHolder;

import java.io.IOException;
import java.net.URL;

import java.util.List;
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
    @FXML
    public TextArea summaryField;
    @FXML
    public TextField publisherField;
    @FXML
    public TextField authorField;
    @FXML
    public TextField illustratorField;
    @FXML
    public Button genreChooserButton;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    GenericHibernate hibernate = new GenericHibernate(entityManagerFactory);
    DataPopulator dataPopulator = new DataPopulator();
    DataTransfer dataTransfer = DataTransfer.getInstance();
    SelectedGenresHolder selectedGenresHolder = SelectedGenresHolder.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAddPublicationLabel();
        setupSpinner();
        fillComboBoxes();
        disableFields();
        setTextFieldFormatter();
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
        } else if (dataTransfer.getText().equals("Book")) {
            volumeNumberField.setDisable(true);
            illustratorField.setDisable(true);
            colorizedCheckBox.setDisable(true);
            demographicComboBox.setDisable(true);
            issueNumberField.setDisable(true);
            editorField.setDisable(true);
            frequencyComboBox.setDisable(true);
        } else if (dataTransfer.getText().equals("Periodical")) {
            volumeNumberField.setDisable(true);
            illustratorField.setDisable(true);
            colorizedCheckBox.setDisable(true);
            demographicComboBox.setDisable(true);
            formatComboBox.setDisable(true);
            genreChooserButton.setDisable(true);
        }
    }

    public void BooleanPropertyReset() {
        for (MangaGenre genre : MangaGenre.values()) {
            genre.setSelected(false);
        }
        for (BookGenre genre : BookGenre.values()) {
            genre.setSelected(false);
        }
    }

    public void createNewPublication(ActionEvent actionEvent) {
        if (isInputEmpty()) {
            return;
        }

        if (dataTransfer.getText().equals("Manga")) {
            Manga manga = new Manga(titleField.getText(), languageComboBox.getValue(), datePicker.getValue(), pageCountSpinner.getValue(), publisherField.getText(), identificationNumberField.getText(), summaryField.getText(), Integer.parseInt(identificationNumberField.getText()), illustratorField.getText(), Integer.parseInt(volumeNumberField.getText()), demographicComboBox.getValue(), (List<MangaGenre>) (List<?>) selectedGenresHolder.getSelectedGenres(), colorizedCheckBox.isSelected());
            hibernate.create(manga);
        } else if (dataTransfer.getText().equals("Book")) {
            Book book = new Book(titleField.getText(), languageComboBox.getValue(), datePicker.getValue(), pageCountSpinner.getValue(), publisherField.getText(), authorField.getText(), summaryField.getText(), Integer.parseInt(identificationNumberField.getText()), (List<BookGenre>) (List<?>) selectedGenresHolder.getSelectedGenres(), formatComboBox.getValue());
            hibernate.create(book);
        } else if (dataTransfer.getText().equals("Periodical")) {
            Periodical periodical = new Periodical(titleField.getText(), languageComboBox.getValue(), datePicker.getValue(), pageCountSpinner.getValue(), publisherField.getText(), authorField.getText(), summaryField.getText(), Integer.parseInt(identificationNumberField.getText()), Integer.parseInt(issueNumberField.getText()), editorField.getText(), frequencyComboBox.getValue());
            hibernate.create(periodical);
        }


        FxUtils.generateAlertWithoutHeader(Alert.AlertType.INFORMATION, "Success", "Publication created successfully");
        BooleanPropertyReset();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setTextFieldFormatter() {
        FxUtils.setTextFieldIntOnly(volumeNumberField);
        FxUtils.setTextFieldIntOnly(identificationNumberField);
        FxUtils.setTextFieldIntOnly(issueNumberField);
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
            if (languageComboBox.getValue() == null || demographicComboBox.getValue() == null || selectedGenresHolder.getSelectedGenres().isEmpty()) {
                isEmpty = true;
            }
        }

        if (dataTransfer.getText().equals("Book") ) {
            if (selectedGenresHolder.getSelectedGenres().isEmpty() || formatComboBox.getValue() == null) {
                isEmpty = true;
            }
        }

        if (dataTransfer.getText().equals("Periodical")) {
            if (frequencyComboBox.getValue() == null) {
                isEmpty = true;
            }
        }

        if (isEmpty) FxUtils.generateAlertWithoutHeader(Alert.AlertType.ERROR, "You missed something!", "Error");
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
