package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static int MEAL_ID = START_SEQ + 3;

    public static final Meal userMeal = new Meal(MEAL_ID + 2,
            LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0, 0), "Ужин", 500);

    public static final Meal newMeal = new Meal(LocalDateTime.of(2024, Month.OCTOBER, 22, 19, 50, 0), "Ужин", 860);
    public static final Meal meal1 = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 23, 59, 10), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal6 = new Meal(MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal meal7 = new Meal(MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static Meal getUpdated() {
        Meal updatedMeal = new Meal(userMeal);
        updatedMeal.setDateTime(LocalDateTime.of(2024, Month.JANUARY, 30, 10, 0));
        updatedMeal.setDescription("Завтрак");
        updatedMeal.setCalories(450);
        return updatedMeal;
    }

    public static List<Meal> getAllTestMeals() {
        return Arrays.asList(meal4, meal7, meal6, meal5, meal3, meal2, meal1);
    }

    public static List<Meal> getTestBetweenInclusive() {
        return Arrays.asList(meal3, meal2, meal1);
    }

    public static void assertMatchListWithSorted(List<Meal> actualMeals, List<Meal> expectedMeals) {
        assertThat(actualMeals).containsExactlyElementsOf(expectedMeals);
    }
}