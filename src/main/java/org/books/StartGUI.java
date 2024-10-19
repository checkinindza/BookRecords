package org.books;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartGUI extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/org.books/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Book exchange");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource(fileName));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
