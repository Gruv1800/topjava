package ru.javawebinar.topjava.repositories.api;

import java.util.List;

public interface Repository<T> {
    void create(T item);
    void upgrade(T item);
    T findById(Number id);
    void delete(T item);
    List<T> getAll();
}
