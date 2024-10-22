package org.books.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.books.Model.enums.MangaGenre;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class MangaGenreConverter implements AttributeConverter<List<MangaGenre>, String> {
    @Override
    public String convertToDatabaseColumn(List<MangaGenre> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(MangaGenre::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<MangaGenre> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(","))
                .map(MangaGenre::valueOf)
                .collect(Collectors.toList());
    }
}
