package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

    public static List<Meal> generateMeals() {
        return Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
    }
    public static void main(String[] args) {
    }

    public static List<MealDTO>  getFilteredWithExcess(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return null;
    }

    public static List<MealDTO> convertMealsToDto(List<Meal> mealList, int caloriesThreshold){
        Map<LocalDate, Integer> totalCaloriesByDate = mealList.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
        return mealList.stream()
                .map(meal -> new MealDTO(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        totalCaloriesByDate.get(meal.getDate()) > caloriesThreshold))
                .collect(Collectors.toList());
    }
}