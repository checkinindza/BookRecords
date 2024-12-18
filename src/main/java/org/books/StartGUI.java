package org.books;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartGUI extends Application {
    private static Stage stage;
    private static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/org.books/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Book exchange");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fileName) throws IOException {
        fxmlLoader = new FXMLLoader(StartGUI.class.getResource(fileName));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void newStage(String fileName, String title) throws IOException {
        Stage stage = new Stage();
        fxmlLoader = new FXMLLoader(StartGUI.class.getResource(fileName));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
