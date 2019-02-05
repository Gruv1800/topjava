package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 14, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 15, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 22, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    /**
     * Get user's meals filtered by time range using Stream API.
     * @param mealList input meals;
     * @param startTime start time interval;
     * @param endTime end time interval;
     * @param caloriesPerDay threshold calories per day;
     * @return List of {@link UserMealWithExceed} or empty list.
     */
    public static List<UserMealWithExceed> getFilteredWithExceededUsingStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> totalCaloriesPerDate = mealList.stream().collect(
                Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories))
        );

        return mealList.stream().filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal, totalCaloriesPerDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    /**
     * Get user's meals filtered by time range.
     * @param mealList input meals;
     * @param startTime start time interval;
     * @param endTime end time interval;
     * @param caloriesPerDay threshold calories per day;
     * @return List of {@link UserMealWithExceed} or empty list.
     */
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> totalCaloriesPerDate = new HashMap<>();
        List<UserMealWithExceed> resultList = new ArrayList<>();

        mealList.forEach(userMeal -> totalCaloriesPerDate.merge(
                userMeal.getDate(), userMeal.getCalories(), (calories, additional) -> calories + additional)
        );

        mealList.forEach(userMeal -> {
            if (TimeUtil.isBetween(userMeal.getTime(), startTime, endTime)) {
                resultList.add(new UserMealWithExceed(userMeal, totalCaloriesPerDate.get(userMeal.getDate()) > caloriesPerDay));
            }
        });
        return resultList;
    }
}
