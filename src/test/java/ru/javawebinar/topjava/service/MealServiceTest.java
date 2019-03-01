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
    }

    @Test
    public void getAll() {
    }

    @Test
    public void update() {
    }

    @Test
    public void create() {
    }
}