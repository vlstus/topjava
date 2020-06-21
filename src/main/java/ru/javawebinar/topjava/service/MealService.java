package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTos;
import static ru.javawebinar.topjava.util.MealsUtil.createToWithSenselessExcessField;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Service
public class MealService {

    @Autowired
    private MealRepository repository;

    public List<MealTo> getAll(int userId) {
        return getFilteredTos(repository.getAll(userId), SecurityUtil.authUserCaloriesPerDay(), LocalTime.MIN, LocalTime.MAX);
    }

    public List<MealTo> getAllFilteredByDateOrTime(Predicate<Meal> filter, int userId) {
        return getFilteredTos(repository.getAllFilteredByDateOrTime(filter, userId), MealsUtil.DEFAULT_CALORIES_PER_DAY, LocalTime.MIN, LocalTime.MAX);
    }

    public MealTo getById(Integer id, int userId) throws NotFoundException {
        return createToWithSenselessExcessField(repository.get(id, userId));
    }

    public boolean deleteById(Integer id, int userId) throws NotFoundException {
        return repository.delete(id, userId);
    }

    public boolean save(Meal mealToSave, int userId) {
        return repository.save(mealToSave, userId) != null;
    }

    public boolean update(Meal mealToUpdate, int mealId, int userId) throws NotFoundException {
        assureIdConsistent(mealToUpdate, mealId);
        return repository.save(mealToUpdate, userId) != null;
    }


}