package org.books.fxControllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.books.Model.enums.BookGenre;
import org.books.Model.enums.MangaGenre;
import org.books.utils.DataPopulator;
import org.books.utils.DataTransfer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GenreChooser implements Initializable {

    @FXML
    public ListView<MangaGenre> genreList;
    @FXML
    public Label notSelectedLabel;

    List<MangaGenre> selectedGenres = new ArrayList<>();
    DataPopulator dataPopulator = new DataPopulator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGenreList();
    }

    public void fillGenreList() {
        DataTransfer dataTransfer = DataTransfer.getInstance();
        if (dataTransfer.getText().equals("Manga")) {
            dataPopulator.fillTableWithEnums(genreList, MangaGenre.class);
        } else if (dataTransfer.getText().equals("Book")) {
            dataPopulator.fillTableWithEnums(genreList, BookGenre.class);
        }
    }

    public void setListCellFactory() {
        genreList.setCellFactory(CheckBoxListCell.forListView(new Callback<MangaGenre, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(MangaGenre item) {
                BooleanProperty observable = new SimpleBooleanProperty();
                observable.addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        selectedGenres.add(item);
                    } else {
                        selectedGenres.remove(item);
                    }
                });
                return observable;
            }
        }));
    }


    public void setupGenreList() {
        fillGenreList();
        setListCellFactory();
    }

    public void sendInfo() {
        DataTransfer dataTransfer = DataTransfer.getInstance();
        if (selectedGenres.isEmpty()) {
            notSelectedLabel.setVisible(true);
        } else {
            notSelectedLabel.setVisible(false);
            dataTransfer.setList(selectedGenres);
            Stage stage = (Stage) genreList.getScene().getWindow();
            stage.close();
        }
    }

    public void exitGenreChooser(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
