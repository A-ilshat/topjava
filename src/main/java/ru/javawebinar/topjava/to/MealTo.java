package ru.javawebinar.topjava.to;

import java.time.LocalDateTime;

public record MealTo(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}