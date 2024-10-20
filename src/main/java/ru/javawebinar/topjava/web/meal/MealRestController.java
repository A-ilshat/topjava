package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        int autUserId = SecurityUtil.authUserId();
        log.info("getting meals");
        List<Meal> meals = service.getAll(autUserId);
        return MealsUtil.getTos(meals, SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        int autUserId = SecurityUtil.authUserId();
        log.info("getting meal {}", id);
        return service.get(id, autUserId);
    }

    public void delete(int id) {
        int autUserId = SecurityUtil.authUserId();
        log.info("deleting meal {}", id);
        service.delete(id, autUserId);
    }

    public void create(Meal meal) {
        int autUserId = SecurityUtil.authUserId();
        log.info("saving meal {}", meal);
        checkNew(meal);
        meal.setUserId(autUserId);
        service.create(meal, autUserId);
    }

    public void update(Meal meal, int id) {
        int autUserId = SecurityUtil.authUserId();
        log.info("updating meal {}", id);
        assureIdConsistent(meal, id);
        meal.setUserId(autUserId);
        service.update(meal, id, autUserId);
    }

    public List<MealTo> getAllByDate(LocalDateTime startDate, LocalTime starTime, LocalDateTime endDate, LocalTime endTime) {
        int autUserId = SecurityUtil.authUserId();
        log.info("getting meals by date");
        List<Meal> meals = service.getAllByDate(startDate, endDate, autUserId);
        return MealsUtil.getFilteredTos(meals, SecurityUtil.authUserCaloriesPerDay(), starTime, endTime);
    }
}