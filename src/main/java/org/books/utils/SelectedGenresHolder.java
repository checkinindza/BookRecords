package org.books.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter

public class SelectedGenresHolder {
    private List<Object> selectedGenres = new ArrayList<>();
    private final static SelectedGenresHolder instance = new SelectedGenresHolder();

    public static SelectedGenresHolder getInstance() {
        return instance;
    }
}
