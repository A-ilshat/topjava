package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudRepository;

    private final EntityManager entityManager;

    public DataJpaMealRepository(CrudMealRepository crudRepository, EntityManager entityManager) {
        this.crudRepository = crudRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = entityManager.getReference(User.class, userId);
        meal.setUser(user);
        if (meal.isNew()) {
            return crudRepository.save(meal);
        } else if (get(meal.getId(), userId) != null) {

            return crudRepository.save(meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Optional<Meal> optional = Optional.ofNullable(crudRepository.findByIdAndUserId(id, userId));
        return optional.orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Optional<List<Meal>> optional = Optional.ofNullable(crudRepository.findAllByUserIdOrderByDateTimeDesc(userId));
        return optional.orElse(null);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetweenHalfOpen(startDateTime, endDateTime, userId);
    }
}