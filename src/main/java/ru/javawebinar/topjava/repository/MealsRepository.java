package ru.javawebinar.topjava.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface MealsRepository<T> {

    List<T> getAll();

    T getById(int id);

    boolean save(T entity);

    boolean update(int id, LocalDateTime dateTime, String description, int calories);

    T deleteById(int id);
}
