package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class MealTestData {

    public static final Meal userMeal = new Meal(100005,
            LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0, 0), "Ужин", 500);

    public static final Meal newMeal = new Meal(LocalDateTime.of(2024, Month.OCTOBER, 22, 19, 50, 0), "Ужин", 860);

    public static final List<Meal> meals = Arrays.asList(
            new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(100009, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    public static Meal getUpdated() {
        Meal updatedMeal = new Meal(userMeal);
        updatedMeal.setDateTime(LocalDateTime.of(2024, Month.JANUARY, 30, 10, 0));
        updatedMeal.setDescription("Завтрак");
        updatedMeal.setCalories(450);
        return updatedMeal;
    }

    public static List<Meal> getTestMealsBySorted() {
        return meals.stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public static List<Meal> getTestBetweenInclusive() {
        return Arrays.asList(
                new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500)
        );
    }

    public static void assertMatchListWithSorted(List<Meal> actualMeals, List<Meal> expectedMeals) {
        assertThat(actualMeals).containsExactlyElementsOf(expectedMeals);
    }

    public static void assertMatchListWithoutSorted(List<Meal> actualMeals, List<Meal> expectedMeals) {
        assertThat(actualMeals).containsExactlyInAnyOrderElementsOf(expectedMeals);
    }
}