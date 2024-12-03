package ru.javawebinar.topjava.web;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void getUsers() throws Exception {
        perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users",
                        new AssertionMatcher<List<User>>() {
                            @Override
                            public void assertion(List<User> actual) throws AssertionError {
                                USER_MATCHER.assertMatch(actual, admin, guest, user);
                            }
                        }
                ));
    }

    @Test
    @DisplayName("getMeals test via assertMatch")
    void getMeals1() throws Exception {
        List<MealTo> expected = List.of(mealTo7, mealTo6, mealTo5, mealTo4, mealTo3, mealTo2, mealTo1);
        perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", new AssertionMatcher<List<MealTo>>() {
                    @Override
                    public void assertion(List<MealTo> actual) throws AssertionError {
                        MEALTO_MATCHER.assertMatch(actual, expected);
                    }
                }));
    }

    @Test
    @DisplayName("getMeals test via expectedValue")
    void getMeals2() throws Exception {
        List<MealTo> meals = List.of(mealTo7, mealTo6, mealTo5, mealTo4, mealTo3, mealTo2, mealTo1);
        perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(7)))
                .andExpect(model().attribute("meals", meals));
    }

    @Test
    void resourceControllerTest() throws Exception {
        perform(get("/resources/css/style.css"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/css;charset=UTF-8"));
    }
}