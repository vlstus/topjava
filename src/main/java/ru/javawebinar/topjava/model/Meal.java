package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Meal {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return calories == meal.calories &&
                Objects.equals(dateTime, meal.dateTime) &&
                Objects.equals(description, meal.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, description, calories);
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public MealTo toUserMealWithExcess(boolean isExcess) {
        return new MealTo(this.dateTime, this.getDescription(), this.calories, isExcess);
    }
}
