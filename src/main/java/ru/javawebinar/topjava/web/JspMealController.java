package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RequestMapping("/meals")
@Controller
public class JspMealController {

    @Autowired
    MealService mealService;


    @GetMapping
    public ModelAndView getAll(ModelAndView modelAndView) {
        modelAndView.setViewName("meals");
        modelAndView.addObject("meals",
                MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return modelAndView;
    }

    @GetMapping(params = {"action=filter", "startDate", "endDate", "startTime", "endTime"})
    public ModelAndView getAllFiltered(@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                       @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                       @RequestParam(value = "startTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                       @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        List<Meal> betweenDates = mealService.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId());
        List<MealTo> betweenTime = MealsUtil.getFilteredTos(betweenDates, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        ModelAndView modelAndView = new ModelAndView("meals");
        modelAndView.addObject("meals", betweenTime);
        return modelAndView;
    }


    @GetMapping(params = "action=create")
    public ModelAndView redirectToCreateForm() {
        ModelAndView mealForm = new ModelAndView("mealForm");
        mealForm.addObject("meal", new MealTo(null, null, null, 0, false));
        return mealForm;
    }

    @PostMapping(params = "action=create")
    public ModelAndView create(@ModelAttribute("mealTo") MealTo mealTo) {
        mealService.create(MealsUtil.createEntity(mealTo), SecurityUtil.authUserId());
        return new ModelAndView("redirect:meals");
    }

    @GetMapping(params = {"action=update", "id"})
    public ModelAndView redirectToUpdateForm(@RequestParam("id") int mealId) {
        ModelAndView mealForm = new ModelAndView("mealForm");
        mealForm.addObject("meal", MealsUtil.createTo(mealService.get(mealId, SecurityUtil.authUserId()), false));
        return mealForm;
    }

    @PostMapping(params = "action=update")
    public ModelAndView update(@ModelAttribute("mealTo") MealTo mealTo) {
        mealService.update(MealsUtil.createEntity(mealTo), SecurityUtil.authUserId());
        return new ModelAndView("redirect:meals");
    }

    @GetMapping(params = {"action=delete", "id"})
    public ModelAndView delete(@RequestParam("id") int mealId) {
        mealService.delete(mealId, SecurityUtil.authUserId());
        return new ModelAndView("redirect:meals");
    }


}
