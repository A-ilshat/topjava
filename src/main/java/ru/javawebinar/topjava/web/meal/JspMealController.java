package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

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

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        log.info("deleting meal with userId {}", userId);

        service.delete(id, userId);
        return "redirect:/meals";
    }


//    @GetMapping
//    public String update(HttpServletRequest request, @ModelAttribute Meal meal) {
//        int userId = SecurityUtil.authUserId();
//        log.info("updating meal with userId {}", userId);
//
//        int id = Integer.parseInt(request.getParameter("id"));
//        service.update(meal, userId);
//        return "";
//    }
}