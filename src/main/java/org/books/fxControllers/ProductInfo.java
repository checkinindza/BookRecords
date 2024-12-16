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
import org.books.Model.*;
import org.books.Model.enums.*;
import org.books.StartGUI;
import org.books.hibernateControllers.CustomHibernate;
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
    DataPopulator dataPopulator = new DataPopulator();
    DataTransfer dataTransfer = DataTransfer.getInstance();
    SelectedGenresHolder selectedGenresHolder = SelectedGenresHolder.getInstance();
    EntityManagerFactory entityManagerFactory;
    CustomHibernate hibernate;

    private Publication publication;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManagerFactory = dataTransfer.getEntityManagerFactory();
        this.hibernate = new CustomHibernate(entityManagerFactory);
        setupSpinner();
        fillComboBoxes();
        setTextFieldFormatter();
        if (dataTransfer.getUpdateWasPressed()) {
            publication = (Publication) dataTransfer.getObject();
            loadPublicationData();
        }
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
        if (publication instanceof Manga || "Manga".equals(dataTransfer.getData())) {
            issueNumberField.setDisable(true);
            editorField.setDisable(true);
            formatComboBox.setDisable(true);
            frequencyComboBox.setDisable(true);
        } else if (publication instanceof Book || "Book".equals(dataTransfer.getData())) {
            volumeNumberField.setDisable(true);
            illustratorField.setDisable(true);
            colorizedCheckBox.setDisable(true);
            demographicComboBox.setDisable(true);
            issueNumberField.setDisable(true);
            editorField.setDisable(true);
            frequencyComboBox.setDisable(true);
        } else if (publication instanceof Periodical || "Periodical".equals(dataTransfer.getData())) {
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

    public void createNewPublication() {
        if (isInputEmpty()) {
            return;
        }
        if (dataTransfer.getData().equals("Manga")) {
            if (dataTransfer.getUser() instanceof Client) {
                Manga manga = new Manga(titleField.getText(),
                        languageComboBox.getSelectionModel().getSelectedItem(),
                        datePicker.getValue(),
                        pageCountSpinner.getValue(),
                        publisherField.getText(),
                        authorField.getText(),
                        summaryField.getText(),
                        PublicationStatus.AVAILABLE,
                        (Client) dataTransfer.getUser(),
                        Integer.parseInt(identificationNumberField.getText()),
                        illustratorField.getText(),
                        Integer.parseInt(volumeNumberField.getText()),
                        demographicComboBox.getSelectionModel().getSelectedItem(),
                        (List<MangaGenre>) (List<?>) selectedGenresHolder.getSelectedGenres(),
                        colorizedCheckBox.isSelected());
                hibernate.create(manga);
            } else {
                Manga manga = new Manga(titleField.getText(),
                        languageComboBox.getSelectionModel().getSelectedItem(),
                        datePicker.getValue(),
                        pageCountSpinner.getValue(),
                        publisherField.getText(),
                        authorField.getText(),
                        summaryField.getText(),
                        PublicationStatus.AVAILABLE,
                        Integer.parseInt(identificationNumberField.getText()),
                        illustratorField.getText(),
                        Integer.parseInt(volumeNumberField.getText()),
                        demographicComboBox.getSelectionModel().getSelectedItem(),
                        (List<MangaGenre>) (List<?>) selectedGenresHolder.getSelectedGenres(),
                        colorizedCheckBox.isSelected());
                hibernate.create(manga);
            }
        } else if (dataTransfer.getData().equals("Book")) {
            if (dataTransfer.getUser() instanceof Client) {
                Book book = new Book(titleField.getText(),
                        languageComboBox.getSelectionModel().getSelectedItem(),
                        datePicker.getValue(),
                        pageCountSpinner.getValue(),
                        publisherField.getText(),
                        authorField.getText(),
                        summaryField.getText(),
                        PublicationStatus.AVAILABLE,
                        (Client) dataTransfer.getUser(),
                        Integer.parseInt(identificationNumberField.getText()),
                        formatComboBox.getSelectionModel().getSelectedItem(),
                        (List<BookGenre>) (List<?>) selectedGenresHolder.getSelectedGenres());
                hibernate.create(book);
            } else {
                Book book = new Book(titleField.getText(),
                        languageComboBox.getSelectionModel().getSelectedItem(),
                        datePicker.getValue(),
                        pageCountSpinner.getValue(),
                        publisherField.getText(),
                        authorField.getText(),
                        summaryField.getText(),
                        PublicationStatus.AVAILABLE,
                        Integer.parseInt(identificationNumberField.getText()),
                        formatComboBox.getSelectionModel().getSelectedItem(),
                        (List<BookGenre>) (List<?>) selectedGenresHolder.getSelectedGenres());
                hibernate.create(book);
            }
        } else if (dataTransfer.getData().equals("Periodical")) {
            if (dataTransfer.getUser() instanceof Client) {
                Periodical periodical = new Periodical(titleField.getText(),
                        languageComboBox.getSelectionModel().getSelectedItem(),
                        datePicker.getValue(),
                        pageCountSpinner.getValue(),
                        publisherField.getText(),
                        authorField.getText(),
                        summaryField.getText(),
                        PublicationStatus.AVAILABLE,
                        (Client) dataTransfer.getUser(),
                        Integer.parseInt(identificationNumberField.getText()),
                        Integer.parseInt(issueNumberField.getText()),
                        editorField.getText(),
                        frequencyComboBox.getSelectionModel().getSelectedItem());
                hibernate.create(periodical);
            } else {
                Periodical periodical = new Periodical(titleField.getText(),
                        languageComboBox.getSelectionModel().getSelectedItem(),
                        datePicker.getValue(),
                        pageCountSpinner.getValue(),
                        publisherField.getText(),
                        authorField.getText(),
                        summaryField.getText(),
                        PublicationStatus.AVAILABLE,
                        Integer.parseInt(identificationNumberField.getText()),
                        Integer.parseInt(issueNumberField.getText()),
                        editorField.getText(),
                        frequencyComboBox.getSelectionModel().getSelectedItem());
                hibernate.create(periodical);
            }
        }


        FxUtils.generateAlertWithoutHeader(Alert.AlertType.INFORMATION, "Success", "Publication created successfully");
        BooleanPropertyReset();
        selectedGenresHolder.getSelectedGenres().clear();
    }

    public void updatePublication() {
        if (isInputEmpty()) {
            return;
        }

        Publication selectedPublication = (Publication) dataTransfer.getObject();
        Publication latestPublication = hibernate.getEntityById(Publication.class, selectedPublication.getId());
        latestPublication.setTitle(titleField.getText());
        latestPublication.setLanguage(languageComboBox.getSelectionModel().getSelectedItem());
        latestPublication.setPublicationDate(datePicker.getValue());
        latestPublication.setPageCount(pageCountSpinner.getValue());
        latestPublication.setPublisher(publisherField.getText());
        latestPublication.setAuthor(authorField.getText());
        latestPublication.setSummary(summaryField.getText());
        latestPublication.setPublicationStatus(PublicationStatus.AVAILABLE);

        if (latestPublication instanceof Manga) {
            Manga manga = (Manga) latestPublication;
            manga.setJan(Integer.parseInt(identificationNumberField.getText()));
            manga.setIllustrator(illustratorField.getText());
            manga.setVolumeNumber(Integer.parseInt(volumeNumberField.getText()));
            manga.setDemographic(demographicComboBox.getSelectionModel().getSelectedItem());
            manga.setMangaGenres((List<MangaGenre>) (List<?>) selectedGenresHolder.getSelectedGenres());
            manga.setIsColor(colorizedCheckBox.isSelected());
            hibernate.update(manga);
        } else if (latestPublication instanceof Book) {
            Book book = (Book) latestPublication;
            book.setIsbn(Integer.parseInt(identificationNumberField.getText()));
            book.setBookGenre((List<BookGenre>) (List<?>) selectedGenresHolder.getSelectedGenres());
            book.setFormat(formatComboBox.getSelectionModel().getSelectedItem());
            hibernate.update(book);
        } else if (latestPublication instanceof Periodical) {
            Periodical periodical = (Periodical) latestPublication;
            periodical.setIssn(Integer.parseInt(identificationNumberField.getText()));
            periodical.setIssueNumber(Integer.parseInt(issueNumberField.getText()));
            periodical.setEditor(editorField.getText());
            periodical.setFrequency(frequencyComboBox.getSelectionModel().getSelectedItem());
            hibernate.update(periodical);
        }

        FxUtils.generateAlertWithoutHeader(Alert.AlertType.INFORMATION, "Success", "Publication updated successfully");
        BooleanPropertyReset();
        selectedGenresHolder.getSelectedGenres().clear();
    }

    public void loadPublicationData() {
        Publication latestPublication = hibernate.getEntityById(Publication.class, publication.getId());
        if (latestPublication instanceof Manga) {
            Manga manga = (Manga) latestPublication;
            titleField.setText(manga.getTitle());
            languageComboBox.setValue(manga.getLanguage());
            datePicker.setValue(manga.getPublicationDate());
            pageCountSpinner.getValueFactory().setValue(manga.getPageCount());
            publisherField.setText(manga.getPublisher());
            identificationNumberField.setText(String.valueOf(manga.getJan()));
            summaryField.setText(manga.getSummary());
            illustratorField.setText(manga.getIllustrator());
            volumeNumberField.setText(String.valueOf(manga.getVolumeNumber()));
            demographicComboBox.setValue(manga.getDemographic());
            colorizedCheckBox.setSelected(manga.getIsColor());
            selectedGenresHolder.setSelectedGenres((List<Object>) (List<?>) manga.getMangaGenres());
        } else if (latestPublication instanceof Book) {
            Book book = (Book) latestPublication;
            titleField.setText(book.getTitle());
            languageComboBox.setValue(book.getLanguage());
            datePicker.setValue(book.getPublicationDate());
            pageCountSpinner.getValueFactory().setValue(book.getPageCount());
            publisherField.setText(book.getPublisher());
            authorField.setText(book.getAuthor());
            summaryField.setText(book.getSummary());
            identificationNumberField.setText(String.valueOf(book.getIsbn()));
            formatComboBox.setValue(book.getFormat());
            selectedGenresHolder.setSelectedGenres((List<Object>) (List<?>) book.getBookGenres());
        } else if (latestPublication instanceof Publication) {
            Periodical periodical = (Periodical) latestPublication;
            titleField.setText(periodical.getTitle());
            languageComboBox.setValue(periodical.getLanguage());
            datePicker.setValue(periodical.getPublicationDate());
            pageCountSpinner.getValueFactory().setValue(periodical.getPageCount());
            publisherField.setText(periodical.getPublisher());
            authorField.setText(periodical.getAuthor());
            summaryField.setText(periodical.getSummary());
            identificationNumberField.setText(String.valueOf(periodical.getIssn()));
            issueNumberField.setText(String.valueOf(periodical.getIssueNumber()));
            editorField.setText(periodical.getEditor());
            frequencyComboBox.setValue(periodical.getFrequency());
        }
    }

    public void setTextFieldFormatter() {
        FxUtils.setTextFieldIntOnly(volumeNumberField);
        FxUtils.setTextFieldIntOnly(identificationNumberField);
        FxUtils.setTextFieldIntOnly(issueNumberField);
    }

    public boolean isInputEmpty() {
        boolean isEmpty = false;
        
        if (FxUtils.areAllFieldsNotEmpty(productInfoPane)) isEmpty = true;

        if ("Manga".equals(dataTransfer.getData()) || publication instanceof Manga) {
            if (languageComboBox.getValue() == null || demographicComboBox.getValue() == null || selectedGenresHolder.getSelectedGenres().isEmpty()) {
                isEmpty = true;
            }
        }

        if ("Book".equals(dataTransfer.getData()) || publication instanceof Book) {
            if (selectedGenresHolder.getSelectedGenres().isEmpty() || formatComboBox.getValue() == null) {
                isEmpty = true;
            }
        }

        if ("Periodical".equals(dataTransfer.getData()) || publication instanceof Periodical) {
            if (frequencyComboBox.getValue() == null) {
                isEmpty = true;
            }
        }

        if (isEmpty) FxUtils.generateAlertWithoutHeader(Alert.AlertType.ERROR, "Error", "You missed something!");
        return isEmpty;
    }

    public void openGenreChooser() throws IOException {
        StartGUI.newStage("/org.books/genreChooser.fxml", "Genre Chooser");
    }

    public void exitProductInfo(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void confirmAction(ActionEvent actionEvent) {
        if (dataTransfer.getAddWasPressed()) {
            createNewPublication();
            dataTransfer.setAddWasPressed(false);
        } else if (dataTransfer.getUpdateWasPressed()) {
            updatePublication();
            dataTransfer.setUpdateWasPressed(false);
        }
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
