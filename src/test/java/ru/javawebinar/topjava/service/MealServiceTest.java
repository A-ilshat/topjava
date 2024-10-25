package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(value = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    static {
        SLF4JBridgeHandler.install();
    }

    @Test
    public void get() {
        Meal actMeal = mealService.get(MEAL_ID + 2, USER_ID);
        assertEquals(userMeal, actMeal);
    }

    @Test
    public void getByAnotherUser() {
        assertThrows(NotFoundException.class, () -> mealService.get(userMeal.getId(), ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_ID - 1, USER_ID));
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_ID + 2, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(userMeal.getId(), USER_ID));
    }

    @Test
    public void deleteByAnotherUser() {
        assertThrows(NotFoundException.class, () -> mealService.delete(MEAL_ID + 2, ADMIN_ID));
    }

    @Test
    public void deleteNotFount() {
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_ID - 1, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate startDate = LocalDate.of(2020, Month.JANUARY, 30);
        LocalDate endDate = LocalDate.of(2020, Month.JANUARY, 30);

        List<Meal> expected = getTestBetweenInclusive();
        List<Meal> actual = mealService.getBetweenInclusive(startDate, endDate, USER_ID);
        assertMatchListWithSorted(actual, expected);
    }

    @Test
    public void getAll() {
        List<Meal> expMeals = getAllTestMeals();
        List<Meal> actMeals = mealService.getAll(USER_ID);
        assertMatchListWithSorted(actMeals, expMeals);
    }

    @Test
    public void update() {
        Meal updatedMeal = getUpdated();
        mealService.update(updatedMeal, USER_ID);
        assertEquals(mealService.get(userMeal.getId(), USER_ID), getUpdated());
    }

    @Test
    public void updateByAnotherUser() {
        Meal updatedMeal = getUpdated();
        assertThrows(NotFoundException.class, () -> mealService.update(updatedMeal, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal expected = newMeal;
        mealService.create(expected, USER_ID);
        Meal actual = mealService.get(expected.getId(), USER_ID);
        assertEquals(expected, actual);
    }
}