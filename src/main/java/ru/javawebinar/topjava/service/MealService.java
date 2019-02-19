package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {
    Meal create(Meal meal);
    void delete(int id) throws NotFoundException;
    void upgrade(Meal meal);
    Meal getById(int id);
    List<Meal> getByUser(int userId);
    List<Meal> getAll();
}