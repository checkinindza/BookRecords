package org.books.Model.enums;

public enum Demographic {
    JAPANESE,
    KOREAN,
    CHINESE,
    EUROPEAN,
    AMERICAN,
    HONG_KONG,
    OTHER;

    @Override
    public String toString() {
        String name = name().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).replace("_", " ");
    }
}
