package org.books.utils;

import javafx.scene.control.Alert;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;

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

    public static List<String> getChildren(Class<?> entityClass) {
        Reflections reflections = new Reflections("org.books");
        Set<Class<?>> subTypes = reflections.get(SubTypes.of(entityClass).asClass());
        List<String> children = new ArrayList<>();
        for (Class<?> subType : subTypes) {
            children.add(subType.getSimpleName());
        }
        return children;
    }
}
