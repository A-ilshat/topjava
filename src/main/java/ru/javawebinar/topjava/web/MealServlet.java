package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealDao mealDao = new MealDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if (req.getParameter("action") != null) {
            String action = req.getParameter("action");
            if (action.equalsIgnoreCase("new")) {
                log.debug("redirect to page for adding a new meal");

                getServletContext().getRequestDispatcher("/meal/new.jsp").forward(req, resp);
            } else if (action.equalsIgnoreCase("edit")) {
                int id = Integer.parseInt(req.getParameter("id"));
                log.debug("redirect to edit meal with id: {}", id);

                Meal meal = mealDao.getById(id);
                req.setAttribute("meal", meal);
                getServletContext().getRequestDispatcher("/meal/edit.jsp").forward(req, resp);
            } else if (action.equalsIgnoreCase("delete")) {
                int id = Integer.parseInt(req.getParameter("id"));
                mealDao.delete(id);

                log.debug("deleted meal with id: {}", id);
                resp.sendRedirect("meals");
            } else if (action.equalsIgnoreCase("clear")) {
                mealDao.deleteAll();

                log.debug("all meals deleted");
                resp.sendRedirect("meals");
            }
        } else {
            List<MealTo> mealsWihExcess = MealsUtil.mealsWithExcess(mealDao.getAll());
            log.debug("redirect to meals page -> count meals: {}, excess meals: {}",
                    mealsWihExcess.size(), MealsUtil.countMealsExcess(mealsWihExcess));
            req.setAttribute("meals", mealsWihExcess);
            getServletContext().getRequestDispatcher("/meal/meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String date = req.getParameter("date");
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        if (req.getParameter("action") != null) {
            log.debug("the entered data in the form: date= {} desc= {} calories= {}", date, description, calories);

            if (req.getParameter("action").equalsIgnoreCase("new")) {
                Meal meal = new Meal();
                meal.setFormatedDateTime(date);
                meal.setDescription(description);
                meal.setCalories(calories);
                mealDao.insert(meal);

                int id = mealDao.getId(meal);
                log.debug("created meal with data: id= {} date= {} desc= {} calories= {}", id, date, description, calories);
                resp.sendRedirect("meals");
            } else if (req.getParameter("action").equalsIgnoreCase("edit")) {
                int id = Integer.parseInt(req.getParameter("id"));
                Meal meal = new Meal();
                meal.setFormatedDateTime(date);
                meal.setDescription(description);
                meal.setCalories(calories);
                mealDao.update(id, meal);

                log.debug("updated meal with data: id= {} date= {} desc= {} calories= {}", id, date, description, calories);
                resp.sendRedirect("meals");
            }
        } else {
            resp.sendRedirect("meals");
        }
    }
}