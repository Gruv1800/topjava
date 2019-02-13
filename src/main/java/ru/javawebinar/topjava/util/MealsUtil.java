package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealDTO;
import ru.javawebinar.topjava.repositories.MealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

    public static List<Meal> generateMeals() {
        List<Meal> result = new ArrayList<>();
        result.addAll(Arrays.asList(
                new Meal(0, LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new Meal(1, LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new Meal(2, LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new Meal(3, LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new Meal(4, LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new Meal(5, LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        ));

        MealRepository.mealCounter += result.size();
        new ArrayList<Meal>();
        return result;
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