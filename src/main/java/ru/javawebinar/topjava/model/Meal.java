package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class Meal extends GenericMeal {

    public Meal(LocalDateTime dateTime, String description, int calories) {
        super(dateTime, description, calories);
    }

    public void update(LocalDateTime dateTime, String description, int calories) {
        setDateTime(dateTime);
        setDescription(description);
        setCalories(calories);
    }

}
