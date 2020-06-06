package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MockMealsRepository;

import java.time.LocalDateTime;
import java.util.List;

public class MealsService {

    private final MockMealsRepository repository;

    public MealsService(MockMealsRepository repository) {
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

    public boolean update(int id, Meal fieldsHolder) {
        return repository.update(id, fieldsHolder);
    }

    public Meal deleteById(int id) {
        return repository.deleteById(id);
    }
}
