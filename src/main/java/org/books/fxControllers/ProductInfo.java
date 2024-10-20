package org.books.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.books.Model.enums.Demographic;
import org.books.Model.enums.Format;
import org.books.Model.enums.Frequency;
import org.books.Model.enums.Language;
import org.books.hibernateControllers.GenericHibernate;

import java.net.URL;
import java.util.Arrays;
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

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    GenericHibernate hibernate = new GenericHibernate(entityManagerFactory);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAddPublicationLabel();
        setupSpinner();
        setupComboBox(Language.class, languageComboBox);
        setupComboBox(Format.class, formatComboBox);
        setupComboBox(Demographic.class, demographicComboBox);
        setupComboBox(Frequency.class, frequencyComboBox);
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

    public <E extends Enum<E>> void setupComboBox(Class<E> enumType, ComboBox<E> comboBox) {
        List<E> choices = Arrays.asList(enumType.getEnumConstants());
        comboBox.getItems().addAll(choices);
    }

    /*public void createNewPublication() {
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
            FxUtils.generateAlertWithoutHeader(Alert.AlertType.INFORMATION, "Success", "User created successfully");
        }
    }*/

    public void setAddPublicationLabel() {
        if (ButtonPressInfo.getInstace().getAddWasPressed()) {
            String title = addPublicationLabel.getText();
            addPublicationLabel.setText(title + " " + ButtonPressInfo.getInstace().getText());
            Text text = new Text(addPublicationLabel.getText());
            text.setFont(addPublicationLabel.getFont());
            text.setStyle(addPublicationLabel.getStyle());
            double width = text.getLayoutBounds().getWidth();
            addPublicationLabel.setMinWidth(width);
            addPublicationLabel.setVisible(true);
        }
    }
}
