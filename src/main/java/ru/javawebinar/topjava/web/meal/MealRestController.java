package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private static final int CURRENT_USER = SecurityUtil.authUserId();

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<Meal> index() {
        log.info("getting meals");
        return service.getAll(CURRENT_USER);
    }

    public Meal get(int id) {
        log.info("getting meal {}", id);
        return service.get(id, CURRENT_USER);
    }

    public void delete(int id) {
        log.info("deleting meal {}", id);
        service.delete(id, CURRENT_USER);
    }

    public void save(Meal meal) {
        log.info("saving meal {}", meal);
        service.create(meal, CURRENT_USER);
    }

    public void update(Meal meal) {
        log.info("updating meal {}", meal);
        service.update(meal, CURRENT_USER);
    }
}