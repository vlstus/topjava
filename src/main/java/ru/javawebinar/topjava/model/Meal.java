package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class Meal extends GenericMeal {

    public Meal(LocalDateTime dateTime, String description, int calories) {
        super(dateTime, description, calories);
    }

    public Meal(int id, LocalDateTime dateTime, String description, int calories) {
        this(dateTime, description, calories);
        setId(id);
    }


    public void update(LocalDateTime dateTime, String description, int calories) {
        setDateTime(dateTime);
        setDescription(description);
        setCalories(calories);
    }

}
