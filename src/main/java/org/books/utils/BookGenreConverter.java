package org.books.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.books.Model.enums.BookGenre;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class BookGenreConverter implements AttributeConverter<List<BookGenre>, String> {
        @Override
        public String convertToDatabaseColumn(List<BookGenre> attribute) {
            if (attribute == null || attribute.isEmpty()) {
                return "";
            }
            return attribute.stream()
                    .map(BookGenre::name)
                    .collect(Collectors.joining(","));
        }

        @Override
        public List<BookGenre> convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.isEmpty()) {
                return List.of();
            }
            return Arrays.stream(dbData.split(","))
                    .map(BookGenre::valueOf)
                    .collect(Collectors.toList());
        }
    }

