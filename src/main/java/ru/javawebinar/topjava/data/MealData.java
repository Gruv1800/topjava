package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealData {
    private static final List<Meal> meals = MealsUtil.generateMeals();

    public static List<Meal> getMeals() {
        return meals;
    }
}
