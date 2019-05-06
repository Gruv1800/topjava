package ru.javawebinar.topjava.web.meals;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL2;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.contentJson;


public class MealRestControllerTest extends AbstractControllerTest {
    public static final String REST_URL = MealRestController.REST_URL + "/";

    @Test
    public void testGet() throws Exception{
        SecurityUtil.setAuthUserId(AbstractBaseEntity.START_SEQ + 1);
        mockMvc.perform(get(REST_URL + ADMIN_MEAL_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN_MEAL1));
    }

    @Test
    public void testGetAll() throws Exception{
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEALS));
    }

    @Test
    public void testDelete() throws Exception{
        SecurityUtil.setAuthUserId(AbstractBaseEntity.START_SEQ + 1);
        mockMvc.perform(delete(REST_URL + ADMIN_MEAL_ID))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertMatch(mealService.getAll(SecurityUtil.authUserId()), ADMIN_MEAL2);
    }

    @Test
    public void testCreate() throws Exception{
        SecurityUtil.setAuthUserId(AbstractBaseEntity.START_SEQ + 1);
        Meal expected = new Meal(of(2015, Month.JUNE, 1, 23, 0),
                "Админ поздний ужин", 500);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Meal returned = TestUtil.readFromJson(action, Meal.class);
        expected.setId(returned.getId());
        assertMatch(mealService.getAll(SecurityUtil.authUserId()), expected, ADMIN_MEAL2, ADMIN_MEAL1);
    }

    @Test
    public void testUpdate() throws Exception{

    }

    @Test
    public void testGetBetween() throws Exception{

    }
}
