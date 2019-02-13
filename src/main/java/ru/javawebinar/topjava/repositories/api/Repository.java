package ru.javawebinar.topjava.repositories.api;

import java.util.List;

public interface Repository<T> {
    void create(T item);
    void upgrade(T item);
    T findById(int id);
    void deleteById(int id);
    List<T> getAll();
}
