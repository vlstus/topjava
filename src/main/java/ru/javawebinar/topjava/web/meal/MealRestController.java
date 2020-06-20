package ru.javawebinar.topjava.web.meal;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
import java.util.function.Predicate;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.util.MealsUtil.combinePredicates;

public class MealRestController {

    private MealService service;

    public List<MealTo> getAll() {
        return service.getAll(authUserId());
    }

    public List<MealTo> getAllFilteredByDateOrTime(Predicate<Meal>... filters) {
        return service.getAllFilteredByDateOrTime(combinePredicates(filters), authUserId());
    }

    public MealTo getById(Integer id) throws NotFoundException {
        return service.getById(id, authUserId());
    }

    public boolean deleteById(Integer id) throws NotFoundException {
        return service.deleteById(id, authUserId());
    }

    public boolean save(Meal mealToSave) {
        return service.save(mealToSave, authUserId());
    }

    public boolean update(Meal mealToUpdate, Integer mealId) throws NotFoundException {
        return service.update(mealToUpdate, mealId, authUserId());
    }
}