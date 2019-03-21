package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Query(value = "DELETE from Meal m WHERE m.id=?1 AND m.user.id=?2")
    boolean delete(int id, int  userId);

    Meal findByIdAndUser(int id, int  userId);

    List<Meal> findByUser(int userId);

    List<Meal> findByDateTimeBetweenAndUser(LocalDateTime startDate, LocalDateTime endDate, int userId);

}
