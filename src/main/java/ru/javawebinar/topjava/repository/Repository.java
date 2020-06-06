package ru.javawebinar.topjava.repository;

import java.util.List;

public interface Repository<T> {

    List<T> getAll();

    T getById(int id);

    boolean save(T entity);

    boolean update(int id, T instanceToStoreFields);

    T deleteById(int id);
}
