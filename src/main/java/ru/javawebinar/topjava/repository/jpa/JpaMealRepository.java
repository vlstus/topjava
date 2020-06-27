package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional()
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        User currentUser = em.getReference(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(currentUser);
            em.persist(meal);
            return meal;
        } else {
            meal.setUser(currentUser);
            if (meal.getUser().id() != userId) {
                throw new IllegalArgumentException("Meal doesn't belong to user with id " + userId);
            }
            return em.merge(meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal mealWithId = em.find(Meal.class, id);
        return mealWithId != null && mealWithId.getUser().id() == userId ? mealWithId : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.ALL_BETWEEN_RANGE)
                .setParameter("user_id", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }
}