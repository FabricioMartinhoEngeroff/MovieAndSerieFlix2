package com.DvFabricio.movieFlix2.model;

public enum Category {
    ACTION,
    ROMANCE,
    COMEDY,
    DRAMA,
    CRIME;


    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No categories found for the given string: " + text);
    }
}

