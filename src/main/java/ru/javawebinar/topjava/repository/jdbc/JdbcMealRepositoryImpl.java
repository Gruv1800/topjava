package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> MEAL_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("YYYY-mm-dd hh:mm:ss");

    public static final String MEALS_TABLE_NAME = "meals";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;


    @Autowired
    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(MEALS_TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource mealMap = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("dateTime", meal.getDateTime().format(DATE_TIME_FORMATTER))
                .addValue("calories", meal.getCalories())
                .addValue("user_id", userId);

        if (meal.isNew()) {
            Number newMealId = insertMeal.executeAndReturnKey(mealMap);
            meal.setId(newMealId.intValue());
        } else if (namedParameterJdbcTemplate.update("UPDATE meals SET user_id=:user_id, description=:description, calories=:calories WHERE id=:id", mealMap) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        MapSqlParameterSource deleteMap = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("user_id", userId);

        return namedParameterJdbcTemplate.update("DELETE from meals WHERE id=:id AND user_id=:user_id", deleteMap) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * from meals WHERE id=? and user_id=?", MEAL_ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * from meals WHERE user_id=?", MEAL_ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Meal> result = jdbcTemplate.query("SELECT * from meals WHERE datetime>=? AND datetime<=? AND user_id=?",
                MEAL_ROW_MAPPER, startDate.format(DATE_TIME_FORMATTER), endDate.format(DATE_TIME_FORMATTER), userId);
        result.sort(Comparator.comparing(Meal::getDateTime));
        return result;
    }
}
