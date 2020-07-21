package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public abstract class AbstractMealController {


    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final MealService service;

    public AbstractMealController(MealService mealService) {
        this.service = mealService;
    }

    protected List<MealTo> getTosBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                         @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        return MealsUtil.getFilteredTos(service.getBetweenInclusive(
                startDate, endDate,
                SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay(),
                startTime, endTime);
    }
}
