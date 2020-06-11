package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealsRepository;


import java.util.ArrayList;
import java.util.List;

public class MealsService {

    private final InMemoryMealsRepository repository;

    public MealsService(InMemoryMealsRepository repository) {
        this.repository = repository;
    }

    public List<Meal> getAllMeals() {
        return new ArrayList<>(repository.getAll());
    }

    public Meal getById(int id) {
        return repository.getById(id);
    }

    public Meal save(Meal entity) {
        return repository.save(entity);
    }

    public boolean update(int id, Meal fieldsHolder) {
        return repository.update(id, fieldsHolder);
    }

    public Meal deleteById(int id) {
        return repository.deleteById(id);
    }
}
