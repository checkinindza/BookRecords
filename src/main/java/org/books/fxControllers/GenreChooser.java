package org.books.fxControllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.books.Interfaces.HasSelectedProperty;
import org.books.Model.Book;
import org.books.Model.Manga;
import org.books.Model.Publication;
import org.books.Model.enums.BookGenre;
import org.books.Model.enums.MangaGenre;
import org.books.utils.DataPopulator;
import org.books.utils.DataTransfer;
import org.books.utils.SelectedGenresHolder;

import java.net.URL;
import java.util.ResourceBundle;

public class GenreChooser implements Initializable {

    @FXML
    public ListView genreList;
    @FXML
    public Label notSelectedLabel;

    SelectedGenresHolder selectedGenresHolder = SelectedGenresHolder.getInstance();
    DataTransfer dataTransfer = DataTransfer.getInstance();
    DataPopulator dataPopulator = new DataPopulator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGenreList();
    }

    public <E extends Enum<E> & HasSelectedProperty> void setListCellFactory(ListView<E> listView, Class <E> enumType) {
        listView.setCellFactory(CheckBoxListCell.forListView(new Callback<E, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(E item) {
                BooleanProperty observable = item.selectedProperty();
                observable.addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected && !selectedGenresHolder.getSelectedGenres().contains(item)) {
                        selectedGenresHolder.getSelectedGenres().add(item);
                    } else {
                        selectedGenresHolder.getSelectedGenres().remove(item);
                    }
                });
                return observable;
            }
        }));
    }


    public void setupGenreList() {
        if (!selectedGenresHolder.getSelectedGenres().isEmpty()) {
            setBooleanProperties();
        }
        if ("Manga".equals(dataTransfer.getText()) || (Publication) dataTransfer.getObject() instanceof Manga) {
            dataPopulator.fillTableWithEnums(genreList, MangaGenre.class);
            setListCellFactory(genreList, MangaGenre.class);
        } else if ("Book".equals(dataTransfer.getText()) || (Publication) dataTransfer.getObject() instanceof Book) {
            dataPopulator.fillTableWithEnums(genreList, BookGenre.class);
            setListCellFactory(genreList, BookGenre.class);
        }
    }

    public void setBooleanProperties() {
        for (Object genre : selectedGenresHolder.getSelectedGenres()) {
            if (genre instanceof MangaGenre) {
                ((MangaGenre) genre).setSelected(true);
            } else if (genre instanceof BookGenre) {
                ((BookGenre) genre).setSelected(true);
            }
        }
    }

    public void sendInfo() {
        if (selectedGenresHolder.getSelectedGenres().isEmpty()) {
            notSelectedLabel.setVisible(true);
        } else {
            notSelectedLabel.setVisible(false);
            Stage stage = (Stage) genreList.getScene().getWindow();
            stage.close();
        }
    }

    public void exitGenreChooser(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
