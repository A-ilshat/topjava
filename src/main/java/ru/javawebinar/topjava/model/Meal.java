package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.FIND_BY_ID, query = "SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:user_id"),
        @NamedQuery(name = Meal.DELETE_BY_ID, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id"),
        @NamedQuery(name = Meal.FIND_ALL_BY_DATE_TIME, query = "SELECT m FROM Meal m WHERE m.user.id=:user_id AND " +
                "dateTime BETWEEN :start_date_time AND :end_date_time ORDER BY dateTime DESC"),
        @NamedQuery(name = Meal.FIND_ALL_SORTED, query = "SELECT m FROM Meal m WHERE m.user.id=:user_id ORDER BY dateTime DESC"),
        @NamedQuery(name = Meal.UPDATE_MEAL, query = "UPDATE Meal m SET m.dateTime=:date_time, m.description=:description," +
                "m.calories=:calories WHERE m.id=:id AND m.user.id=:user_id")
})

@Entity
@Table(name = "meal", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"})})
public class Meal extends AbstractBaseEntity {

    public static final String DELETE_BY_ID = "Meal.deleteById";
    public static final String FIND_BY_ID = "Meal.findById";
    public static final String FIND_ALL_BY_DATE_TIME = "Meal.findAllByDateTime";
    public static final String FIND_ALL_SORTED = "Meal.findAllSorted";
    public static final String UPDATE_MEAL = "Meal.updateMeal";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 1, max = 255)
    private String description;

    @Column(name = "calories", nullable = false)
    @Positive
    @Min(value = 10)
    @Max(value = 5000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
