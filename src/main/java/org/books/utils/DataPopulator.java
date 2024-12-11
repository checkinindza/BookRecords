package org.books.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import org.books.hibernateControllers.CustomHibernate;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;

public class DataPopulator {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-shop");
    CustomHibernate hibernate = new CustomHibernate(entityManagerFactory);

    public void fillTableWithAllRecords(ListView table, Class <?> entityClass) {
        table.getItems().clear();
        List<?> itemList = hibernate.getAllRecords(entityClass);
        table.getItems().addAll(itemList);
    }

    public void fillTableWithRecordsByCriteria(ListView table, Class <?> entityClass, String criteria, String value) {
        table.getItems().clear();
        List<?> itemList = hibernate.getRecordsByCriteria(entityClass, criteria, value);
        table.getItems().addAll(itemList);
    }

    public <E extends Enum<E>> void fillTableWithEnums(ListView table, Class<E> enumType) {
        table.getItems().clear();
        List<E> itemList = Arrays.asList(enumType.getEnumConstants());
        table.getItems().addAll(itemList);
    }

    public List<String> getChildren(Class<?> entityClass) {
        Reflections reflections = new Reflections("org.books");
        Set<Class<?>> subTypes = reflections.get(SubTypes.of(entityClass).asClass());
        List<String> children = new ArrayList<>();
        for (Class<?> subType : subTypes) {
            children.add(subType.getSimpleName());
        }
        return children;
    }

    public <E extends Enum<E>> void fillComboBoxWithEnums(Class<E> enumType, ComboBox<E> comboBox) {
        List<E> choices = Arrays.asList(enumType.getEnumConstants());
        comboBox.getItems().addAll(choices);
    }

    public void fillComboBox(ComboBox comboBox, List<String> items) {
        comboBox.getItems().addAll(items);
    }
}
