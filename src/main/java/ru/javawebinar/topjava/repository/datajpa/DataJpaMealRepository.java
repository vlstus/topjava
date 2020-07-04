package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.SpecificationsUtil;


import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
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

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.getUser() == null) {
            meal.setUser(userRepository.getOne(userId));
        }
        return crudRepository.save(meal);
    }

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
        return crudRepository.getAllByUserId(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {

        Specification.where(SpecificationsUtil.<Meal, Integer>fieldIsEqual(userId, "user", "id")
                .and(SpecificationsUtil.greaterThanOrEquals(startDateTime, "dateTime"))
                .and(SpecificationsUtil.lessThen(endDateTime, "dateTime")));


        return crudRepository.findAll
                (Specification.where
                        (((Specification<Meal>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId))
                                .and(((Specification<Meal>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dateTime"), startDateTime))
                                        .and((Specification<Meal>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("dateTime"), endDateTime)))))
                .stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}
