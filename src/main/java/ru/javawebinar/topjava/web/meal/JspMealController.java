package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private final Logger log = LoggerFactory.getLogger(JspMealController.class);

    private final MealService service;

    @Autowired
    public JspMealController(MealService service) {
        this.service = service;
    }

    @GetMapping()
    public String getAll(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getting meals for user {}", userId);

        List<MealTo> meals = MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", meals);
        return "meals";
    }

    @GetMapping("/filter")
    public String getFilteredAll(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                 @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                 @RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                 @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
                                 Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getting filtered meals for userId {}", userId);

        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        List<MealTo> meals = MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        model.addAttribute("meals", meals);
        return "meals";
    }


    @GetMapping("/create-form")
    public String getCreateForm(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getting create form for userId {}", userId);

        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("meal") Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("creating new meal for userId: {}", userId);

        service.create(meal, userId);
        return "redirect:/meals";
    }

    @GetMapping("/{id}/edit-form")
    public String getUpdateForm(@PathVariable int id, Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getting edit form for meal with id: {} and userId: {}", id, userId);

        model.addAttribute("meal", service.get(id, userId));
        return "mealForm";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable int id, @ModelAttribute("meal") Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("updating meal id: {} for userId: {}", id, userId);

        service.update(meal, userId);
        return "redirect:/meals";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        log.info("deleting meal with userId {}", userId);

        service.delete(id, userId);
        return "redirect:/meals";
    }
}