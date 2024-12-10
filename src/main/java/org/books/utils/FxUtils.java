package org.books.utils;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.regex.Pattern;

public class FxUtils {
    public static void generateAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(content);
        alert.setContentText(title);
        alert.showAndWait();
    }

    public static void generateAlertWithoutHeader(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean areAllFieldsNotEmpty(Pane parent) {
        return parent.getChildren().stream()
                .filter(node -> node instanceof TextInputControl)
                .map(node -> (TextInputControl) node)
                .filter(field -> !(field instanceof PasswordField))
                .allMatch(field -> !field.getText().isEmpty());
    }

    public static void setTextFieldIntOnly(TextField textField) {
        Pattern pattern = Pattern.compile("\\d*");
        textField.setTextFormatter(new TextFormatter<>(c -> {
            if (pattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        }));
    }
}
