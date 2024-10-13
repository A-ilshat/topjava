package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MealDao {
    private static final Logger log = LoggerFactory.getLogger(MealDao.class);
    public static final List<Meal> meals = new ArrayList<>();

    static {
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public List<Meal> getAll() {
        return meals;
    }

    public Meal getById(int id) {
        return meals.get(id);
    }

    public void insert(Meal meal) {
        meals.add(meal);
    }

    public void delete(int index) {
        meals.remove(index);
    }

    public void deleteAll() {
        meals.clear();
    }

    public void update(int id, Meal meal) {
        meals.set(id, meal);
    }

    public int getId(Meal meal) {
        return meals.indexOf(meal);
    }
}