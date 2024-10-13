package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int caloriesPerDay = 2000;

    public static void main(String[] args) {
        List<MealTo> mealsTo = filteredByStreams(MealDao.meals, LocalTime.of(7, 0), LocalTime.of(12, 0), caloriesPerDay);
        mealsTo.forEach(System.out::println);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = getCaloriesSumByDate(meals);

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> getCaloriesSumByDate(List<Meal> meals) {
        return meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static List<MealTo> mealsWithExcess(List<Meal> meals) {
        return meals.stream()
                .map(meal -> createTo(meal, getCaloriesSumByDate(meals).get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static int countMealsExcess(List<MealTo> meals) {
        return (int) meals.stream().filter(MealTo::isExcess).count();
    }
}