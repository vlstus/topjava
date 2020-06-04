package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MockMealsRepository;

import java.util.List;

public class MealsService {

    private final MockMealsRepository repository;

    public MealsService(MockMealsRepository repository) {
        this.repository = repository;
    }

    public List<Meal> getAllMeals() {
        return repository.getAll();
    }
}
