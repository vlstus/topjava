package ru.javawebinar.topjava.web.meal;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
import java.util.function.Predicate;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class MealRestController {
    private MealService service;

    public List<MealTo> getAll() {
        return null;
    }

    public List<MealTo> getAllFilteredByDateOrTime() {
        Predicate<Meal> filter;
        return null;
    }

    public MealTo getById(Integer id) throws NotFoundException {
        return null;
    }

    public MealTo deleteById(Integer id) throws NotFoundException {
        return null;
    }

    public boolean save(Meal mealToSave) {
        return false;
    }

    public boolean update(Meal mealToUpdate, Integer mealId) throws NotFoundException {
        return false;
    }
}