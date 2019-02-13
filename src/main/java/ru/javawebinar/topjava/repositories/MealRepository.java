package ru.javawebinar.topjava.repositories;

import ru.javawebinar.topjava.data.MealData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repositories.api.Repository;

import java.util.List;

public class MealRepository implements Repository<Meal> {
    public static int mealCounter = 0;
    @Override
    public void create(Meal item) {
        MealData.getMeals().add(item);
    }

    @Override
    public void upgrade(Meal item) {
        MealData.getMeals().stream().filter(meal -> meal.getId()==item.getId()).forEach(meal -> {
            meal.setCalories(item.getCalories());
            meal.setDateTime(item.getDateTime());
            meal.setDescription(item.getDescription());
        });
    }

    @Override
    public Meal findById(int id) {
        return MealData.getMeals().stream().filter(meal -> meal.getId()==id).findFirst().get();
    }

    @Override
    public void deleteById(int id) {
        MealData.getMeals().remove(findById(id));
    }

    @Override
    public List<Meal> getAll() {
        return MealData.getMeals();
    }
}
