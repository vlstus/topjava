package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.SpecificationsUtil;


import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;

    private final CrudUserRepository userRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository,
                                 CrudUserRepository userRepository) {
        this.crudRepository = crudRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUser(userRepository.getOne(userId));
            return crudRepository.save(meal);
        } else {
            Meal actualStoredMeal = crudRepository.getOne(meal.id());
            if (actualStoredMeal.getUser().id() != userId) {
                return null;
            } else {
                meal.setUser(userRepository.getOne(userId));
                return crudRepository.save(meal);
            }
        }
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.getByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAllByUserId(userId)
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.findAll(Specification.where(
                Objects.requireNonNull(SpecificationsUtil.<Meal, Integer>fieldIsEqual(userId, "user", "id")
                .and(SpecificationsUtil.greaterThanOrEquals(startDateTime, "dateTime")))
                .and(SpecificationsUtil.lessThen(endDateTime, "dateTime"))),
                Sort.by(Sort.Direction.DESC, "dateTime"));

    }
}
