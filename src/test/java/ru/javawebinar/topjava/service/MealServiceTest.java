package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@RunWith(SpringRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-test-context.xml"
})
public class MealServiceTest {


    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal expected = new Meal(100002, LocalDateTime.of(2019, 2, 28, 19, 28, 47),
                "Lunch", 3000);
        Meal actual = service.get(100002, 100000);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void delete() {
        Meal newMeal = service.create(new Meal(null, LocalDateTime.of(2019, 3, 11, 19, 28, 47),
                "Lunch", 2111), 100000);
        service.delete(newMeal.getId(), 100000);
        assertThatThrownBy(() -> service.get(newMeal.getId(), 100000)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime startTime = LocalDateTime.of(2019, 2, 28, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2019, 2, 28, 20, 0, 0);
        List<Meal> meals = service.getBetweenDateTimes(startTime, endTime, 100000);
        assertThat(2).isEqualTo(meals.size());

    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(100000);
        assertThat(4).isEqualTo(all.size());
    }

    @Test
    public void update() {
        Meal upgradedMeal = new Meal(100002, LocalDateTime.now(), "Updated", 33333);
        service.update(upgradedMeal, 100000);
        Meal mealFromDb = service.get(100002, 100000);
        assertThat(mealFromDb.getCalories()).isEqualTo(33333);
        assertThat(mealFromDb.getDescription()).isEqualTo("Updated");
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "New Meal test", 55555);
        Meal createdMeal = service.create(newMeal, 100001);
        newMeal.setId(createdMeal.getId());
        assertThat(newMeal).isEqualTo(createdMeal);
    }
}