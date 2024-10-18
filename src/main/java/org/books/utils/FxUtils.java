package org.books.utils;

import javafx.scene.control.Alert;

public class FxUtils {
    public static void generateAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(content);
        alert.setContentText(title);
        alert.showAndWait();
    }

    public static void generateAlertWithoutHeader(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(content);
        alert.setHeaderText(null);
        alert.setContentText(title);
        alert.showAndWait();
    }
}
