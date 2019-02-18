package ru.javawebinar.topjava.to;

import java.time.LocalDateTime;

public class MealTo {
    private final Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final int userId;

    private final boolean excess;

    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories, int userId, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.userId = userId;
        this.excess = excess;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isExcess() {
        return excess;
    }
}
