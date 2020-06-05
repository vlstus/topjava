package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class MealTo extends GenericMeal {


    private boolean excess;

    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess) {
        super(dateTime, description, calories);
        this.excess = excess;
    }

    public MealTo(int id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this(dateTime, description, calories, excess);
        setId(id);
    }

    public boolean isExcess() {
        return excess;
    }

    public void setExcess(boolean excess) {
        this.excess = excess;
    }

    @Override
    public String toString() {

        return "MealTo{" +
                "dateTime=" + DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(dateTime) +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
