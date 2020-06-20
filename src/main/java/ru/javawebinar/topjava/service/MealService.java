package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
import java.util.function.Predicate;

public class MealService {

    private MealRepository repository;

    public List<MealTo> getAll(int userId) {
        return null;
    }

    public List<MealTo> getAllFilteredByDateOrTime(Predicate<Meal> filter) {
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