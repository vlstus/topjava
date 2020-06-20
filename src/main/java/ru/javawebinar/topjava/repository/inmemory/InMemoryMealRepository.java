package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, List<Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        User mockUser = new User(null, "First", "FirstEmail@example.com", "firstPassword", 2000, true, Collections.singleton(Role.USER));
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        inMemoryUserRepository.save(mockUser);
        MealsUtil.MEALS.forEach(meal -> this.save(meal, mockUser.getId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        List<Meal> mealsForUser = repository.computeIfAbsent(userId, id -> Collections.synchronizedList(new ArrayList<>()));
        if (meal.isNew()) {
            return createMeal(meal, mealsForUser);
        } else {
            return updateMeal(meal, mealsForUser);
        }

    }

    private Meal createMeal(Meal meal, List<Meal> mealsForUser) {
        meal.setId(counter.incrementAndGet());
        mealsForUser.add(meal);
        return meal;
    }

    private Meal updateMeal(Meal meal, List<Meal> mealsForUser) {
        Meal oldMeal = mealsForUser.stream()
                .filter(currentMeal -> currentMeal.getId().equals(meal.getId()))
                .findAny()
                .orElse(null);
        if (oldMeal != null) {
            mealsForUser.remove(oldMeal);
            mealsForUser.add(meal);
            return oldMeal;
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        List<Meal> mealsForUser = repository.get(userId);
        if (mealsForUser == null) {
            return false;
        } else {
            return mealsForUser.removeIf(currentMeal -> currentMeal.getId().equals(id));
        }
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> mealsForUser = repository.get(userId);
        if (mealsForUser == null) {
            return null;
        } else {
            return mealsForUser
                    .stream()
                    .filter(currentMeal -> currentMeal.getId().equals(id))
                    .findAny()
                    .orElse(null);
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return Objects.requireNonNullElse(repository.get(userId), Collections.emptyList());
    }

    public List<Meal> getAllFilteredByDateOrTime(Predicate<Meal> filter, int userId) {
        return getAll(userId)
                .stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
}

