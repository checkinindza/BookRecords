package org.books.Model.enums;

public enum Frequency {
    ONCE_PER_5_YEARS,
    ONCE_PER_4_YEARS,
    ONCE_PER_3_YEARS,
    ONCE_PER_2_YEARS,
    ONCE_PER_YEAR,
    TWICE_PER_YEAR,
    THRICE_PER_YEAR,
    EVERY_QUARTER,
    EVERY_2_MONTHS,
    TWICE_PER_QUARTER,
    EVERY_MONTH,
    TWICE_PER_MONTH,
    EVERY_TWO_WEEKS,
    EVERY_WEEK,
    TWICE_PER_WEEK,
    ONCE_PER_BUSINESS_DAY;

    @Override
    public String toString() {
        String name = name().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).replace("_", " ");
    }
}
