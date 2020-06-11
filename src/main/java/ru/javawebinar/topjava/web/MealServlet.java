package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MockMealsRepository;
import ru.javawebinar.topjava.service.MealsService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(UserServlet.class);

    private final MealsService service;

    public MealServlet() {
        service = new MealsService(new MockMealsRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            listAllMeal(req, resp);
        } else {
            log.debug("Performing action : " + action);
            switch (action) {
                case "create":
                case "update": {
                    dispatchToMealsForm(req, resp);
                    break;
                }
                case "delete": {
                    doDelete(req, resp);
                    break;
                }
                case "read": {
                    readById(req, resp);
                    break;
                }
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("u-M-dTH:m");
        String id = req.getParameter("id");
        if (id != null) {
            log.debug("Updating " + id);
            service.update(Integer.parseInt(id),
                    new Meal(Integer.parseInt(req.getParameter("id")),
                            LocalDateTime.parse(req.getParameter("date")),
                            req.getParameter("desc"),
                            Integer.parseInt(req.getParameter("calories"))));
        } else {
            log.debug("Saving " + req.getParameterMap());
            service.save(new Meal(LocalDateTime.parse(req.getParameter("date")),
                    req.getParameter("desc"),
                    Integer.parseInt(req.getParameter("calories"))));
        }
        listAllMeal(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Deleting " + req.getParameter("id"));
        service.deleteById(Integer.parseInt(req.getParameter("id")));
        listAllMeal(req, resp);
    }

    private void listAllMeal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Dispatching to mealsList");
        req.setAttribute("mealsList", MealsUtil.filteredByStreams(service.getAllMeals()));
        req.getRequestDispatcher("views/meals.jsp").forward(req, resp);
    }

    private void dispatchToMealsForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            log.debug("Editing " + id);
            Meal meal = service.getById(Integer.parseInt(id));
            req.setAttribute("meal", meal);
        } else {
            log.debug("Creating " + req.getParameterMap());
        }
        req.getRequestDispatcher("views/meal_editor.jsp").forward(req, resp);
    }

    private void readById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Reading " + req.getParameter("id"));
        req.setAttribute("mealsList",
                MealsUtil.filteredByStreams
                        (List.of
                                (service.getById(Integer.parseInt(req.getParameter("id"))))));
        req.getRequestDispatcher("views/meals.jsp").forward(req, resp);
    }
}
