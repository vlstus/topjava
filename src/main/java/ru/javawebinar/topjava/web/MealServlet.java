package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.Predicate;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;

    @Autowired
    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            controller.save(meal);
        } else {
            controller.update(meal, meal.getId());
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");


        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.deleteById(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        MealsUtil.createEntity(controller.getById(getId(request)));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                request.setAttribute("meals",
                        controller.getAllFilteredByDateOrTime(formPredicate(request)));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "mealsForId":
                SecurityUtil.setAuthUserId(Integer.parseInt(request.getParameter("userId")));
                response.sendRedirect("meals");
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getTos(MealsUtil.getEntities(controller.getAll()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    private Predicate<Meal> formPredicate(HttpServletRequest request) {
        return MealsUtil.combinePredicates(
                meal -> {
                    LocalDate[] localDates = parseDates(request);
                    return DateTimeUtil.isBetweenInclusive(meal.getDate(), localDates[0], localDates[1]);
                },
                meal -> {
                    LocalTime[] localTimes = parseTimes(request);
                    return DateTimeUtil.isBetweenInclusive(meal.getTime(), localTimes[0], localTimes[1]);
                });
    }

    private LocalDate[] parseDates(HttpServletRequest request) {
        LocalDate[] localDates = new LocalDate[2];
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if (startDate.isEmpty()) {
            localDates[0] = LocalDate.MIN;
        } else {
            localDates[0] = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        }
        if (endDate.isEmpty()) {
            localDates[1] = LocalDate.MAX;
        } else {
            localDates[1] = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        }
        return localDates;
    }

    private LocalTime[] parseTimes(HttpServletRequest request) {
        LocalTime[] localTimes = new LocalTime[2];
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        if (startTime.isEmpty()) {
            localTimes[0] = LocalTime.MIN;
        } else {
            localTimes[0] = LocalTime.parse(startTime);
        }
        if (endTime.isEmpty()) {
            localTimes[1] = LocalTime.MAX;
        } else {
            localTimes[1] = LocalTime.parse(endTime);
        }
        return localTimes;
    }


}
