package ru.javawebinar.topjava.repository;

import java.util.Collection;


public interface Repository<T> {

    Collection<T> getAll();

    T getById(int id);

    T save(T entity);

    boolean update(int id, T instanceToStoreFields);

    T deleteById(int id);
}
