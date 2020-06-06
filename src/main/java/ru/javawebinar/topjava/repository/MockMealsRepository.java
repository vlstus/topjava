package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class MockMealsRepository implements Repository<Meal> {

    private static volatile AtomicInteger currentRepoID = new AtomicInteger();

    private List<Meal> mockData = new ArrayList<>(Arrays.asList(
            new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    ));

    @Override
    public synchronized List<Meal> getAll() {
        return Collections.unmodifiableList(mockData);
    }

    @Override
    public synchronized Meal getById(int id) {
        return mockData.stream()
                .filter(meal -> meal.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public synchronized boolean save(Meal entity) {
        entity.setId(currentRepoID.getAndIncrement());
        return mockData.add(entity);
    }

    @Override
    public synchronized boolean update(int id, Meal instanceToStoreFields) {
        Meal mealFound = null;
        for (Meal meal :
                mockData) {
            if (meal.getId() == id) {
                mealFound = meal;
                break;
            }
        }
        if (mealFound != null) {
            mealFound.setDateTime(instanceToStoreFields.getDateTime());
            mealFound.setDescription(instanceToStoreFields.getDescription());
            mealFound.setCalories(instanceToStoreFields.getCalories());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized Meal deleteById(int id) {
        Meal mealFound = null;
        for (Meal meal :
                mockData) {
            if (meal.getId() == id) {
                mealFound = meal;
                break;
            }
        }
        if (mealFound != null) {
            mockData.remove(mealFound);
        }
        return mealFound;
    }


}
