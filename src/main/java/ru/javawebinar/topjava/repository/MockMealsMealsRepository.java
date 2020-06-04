package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


public class MockMealsMealsRepository implements MealsRepository<Meal> {

    private final Map<Integer, Meal> mockData = new HashMap<>() {
        {
            put(0, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
            put(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
            put(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
            put(3, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
            put(4, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
            put(5, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
            put(6, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        }
    };


    @Override
    public synchronized List<Meal> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(mockData.values()));
    }

    @Override
    public synchronized Meal getById(int id) {
        return mockData.getOrDefault(id, null);
    }

    @Override
    public synchronized boolean save(Meal entity) {
        for (int i = 0; ; i++) {
            if (mockData.putIfAbsent(i, entity) == null) {
                return true;
            }
        }
    }

    @Override
    public boolean update(int id, LocalDateTime dateTime, String description, int calories) {
        Meal meal = mockData.get(id);
        if (meal != null) {
            meal.update(dateTime, description, calories);
        }
        return false;
    }


    @Override
    public synchronized Meal deleteById(int id) {
        return mockData.remove(id);
    }


}
