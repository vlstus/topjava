package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.service.MealService;

public abstract class AbstractMealController {

    protected final MealService service;

    public AbstractMealController(MealService mealService) {
        this.service = mealService;
    }
}
