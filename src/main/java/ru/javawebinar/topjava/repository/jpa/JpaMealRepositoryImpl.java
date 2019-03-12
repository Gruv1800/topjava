package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em.find(User.class, userId);
        meal.setUser(user);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            Meal upgradableMeal = null;
            try {
                upgradableMeal = em.createNamedQuery(Meal.GET_BY_ID_AND_USER, Meal.class)
                        .setParameter("id", meal.getId())
                        .setParameter("user", user)
                        .getSingleResult();
            } catch (NoResultException e) {
                em.persist(meal);
                return meal;
            }
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user", em.find(User.class, userId))
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        try {
            return em.createNamedQuery(Meal.GET_BY_ID_AND_USER, Meal.class)
                    .setParameter("id", id)
                    .setParameter("user", em.find(User.class, userId))
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        User user = em.find(User.class, userId);
        return em.createNamedQuery(Meal.GET_BY_USER, Meal.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User user = em.find(User.class, userId);
        return em.createNamedQuery(Meal.GET_BETWEEN_BY_USER, Meal.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("user", user)
                .getResultList();
    }
}