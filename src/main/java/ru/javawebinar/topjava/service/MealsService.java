package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MockMealsMealsRepository;

import java.time.LocalDateTime;
import java.util.List;

public class MealsService {

    private final MockMealsMealsRepository repository;

    public MealsService(MockMealsMealsRepository repository) {
        this.repository = repository;
    }

    public List<Meal> getAllMeals() {
        return repository.getAll();
    }

    public Meal getById(int id) {
        return repository.getById(id);
    }

    public boolean save(Meal entity) {
        return repository.save(entity);
    }

    public boolean update(int id, LocalDateTime dateTime, String description, int calories) {
        return repository.update(id, dateTime, description, calories);
    }

    public Meal deleteById(int id) {
        return repository.deleteById(id);
    }
}
