package ru.javawebinar.topjava.web.meal;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;


import java.time.LocalDate;
import java.time.LocalTime;


@RequestMapping("/meals")
@Controller
public class JspMealController extends AbstractMealController {


    public JspMealController(MealService mealService) {
        super(mealService);
    }


    @GetMapping
    public ModelAndView getAll(ModelAndView modelAndView) {
        int userId = SecurityUtil.authUserId();
        log.info("get all meal for user {}", userId);
        modelAndView.setViewName("meals");
        modelAndView.addObject("meals",
                MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return modelAndView;
    }

    @GetMapping(params = {"action=filter", "startDate", "endDate", "startTime", "endTime"})
    public ModelAndView getAllFiltered(@Nullable @RequestParam(value = "startDate", required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                       @Nullable @RequestParam(value = "endDate", required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                       @Nullable @RequestParam(value = "startTime", required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                       @Nullable @RequestParam(value = "endTime", required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        return new ModelAndView("meals")
                .addObject("meals", getTosBetween(startDate, startTime, endDate, endTime));
    }


    @PostMapping(params = "action=create")
    public ModelAndView create(@ModelAttribute("mealTo") MealTo mealTo) {
        int userId = SecurityUtil.authUserId();
        log.info("create meal for user {}", userId);
        service.create(MealsUtil.createEntity(mealTo), SecurityUtil.authUserId());
        return new ModelAndView("redirect:meals");
    }

    @PostMapping(params = "action=update")
    public ModelAndView update(@ModelAttribute("mealTo") MealTo mealTo) {
        int userId = SecurityUtil.authUserId();
        log.info("update meal {} for user {}", mealTo.getId(), userId);
        service.update(MealsUtil.createEntity(mealTo), SecurityUtil.authUserId());
        return new ModelAndView("redirect:meals");
    }

    @GetMapping(params = {"action=delete", "id"})
    public ModelAndView delete(@RequestParam("id") int mealId) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", mealId, userId);
        service.delete(mealId, SecurityUtil.authUserId());
        return new ModelAndView("redirect:meals");
    }


    @GetMapping(params = "action")
    public ModelAndView dispatchCreateAndUpdate(
            @RequestParam("action") String action,
            @RequestParam(value = "id", required = false) Integer id) {
        ModelAndView mealForm = new ModelAndView("mealForm");
        switch (action) {
            case "create" -> {
                mealForm.addObject("meal",
                        new MealTo(null, null, null, 0, false));
                return mealForm;
            }
            case "update" -> {
                mealForm.addObject("meal",
                        MealsUtil.createTo(service.get(id, SecurityUtil.authUserId()), false));
                return mealForm;
            }
            default -> throw new IllegalArgumentException();
        }
    }

}
