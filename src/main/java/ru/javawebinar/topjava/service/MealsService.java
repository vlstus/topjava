package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MockMealsMealsRepository;

import java.util.List;

public class MealsService {

    private final MockMealsMealsRepository repository;

    public MealsService(MockMealsMealsRepository repository) {
        this.repository = repository;
    }

    public List<Meal> getAllMeals() {
        return repository.getAll();
    }
}
