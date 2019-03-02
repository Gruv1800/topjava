package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public class MealTestData {
    public static final Meal MEAL_1 = new Meal(LocalDateTime.now(), "Lunch", 3000);
    public static final Meal MEAL_2 = new Meal(LocalDateTime.now(), "Dinner", 1500);
    public static final Meal MEAL_3 = new Meal(LocalDateTime.now(), "Breakfast", 2000);

}
