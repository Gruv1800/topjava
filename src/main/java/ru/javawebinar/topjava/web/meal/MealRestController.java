package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;

import java.util.List;

@Controller
public class MealRestController {
    private final static Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public MealTo create(Meal meal) {
        return MealsUtil.createWithExcess(service.create(meal), false);
    }
    public void delete(int id) {
        service.delete(id);
    }
    public void upgrade(Meal meal) {
        service.upgrade(meal);
    }
    public MealTo getById(int id) {
        return MealsUtil.createWithExcess(service.getById(id), false);
    }
    public List<MealTo> getByUser() {
        return MealsUtil.getWithExcess(service.getByUser(authUserId()), authUserCaloriesPerDay());
    }
    public List<MealTo> getAll() {
        return MealsUtil.getWithExcess(service.getAll(), authUserCaloriesPerDay());
    }
}