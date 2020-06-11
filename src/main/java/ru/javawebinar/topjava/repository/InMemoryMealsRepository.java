package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryMealsRepository implements Repository<Meal> {

    private static volatile AtomicInteger currentRepoID = new AtomicInteger();
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();

    {
        Arrays.asList(
                new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ).forEach(this::save);
    }

    @Override
    public Collection<Meal> getAll() {
        return Collections.unmodifiableCollection(mealMap.values());
    }

    @Override
    public Meal getById(int id) {
        return mealMap.get(id);
    }

    @Override
    public Meal save(Meal entity) {
        if (entity.getId() == null) {
            entity.setId(currentRepoID.getAndIncrement());
        }
        return mealMap.put(entity.getId(), entity);
    }

    @Override
    public boolean update(int id, Meal instanceToStoreFields) {
        if (mealMap.containsKey(id)) {
            final Meal meal = mealMap.get(id);
            meal.update(
                    instanceToStoreFields.getDateTime(),
                    instanceToStoreFields.getDescription(),
                    instanceToStoreFields.getCalories());
            return true;
        }
        return false;
    }

    @Override
    public Meal deleteById(int id) {
        return mealMap.remove(id);
    }


}
