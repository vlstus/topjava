package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping(value = "/rest/to/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestControllerTransferObjects extends AbstractMealController {

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "{id}")
    public MealTo getTo(@PathVariable int id) {
        return super.getAll().stream()
                .filter(mealTo -> mealTo.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    @GetMapping(params = {"startTime", "startDate", "endTime", "endDate"})
    public List<MealTo> getBetween(
            @Nullable @RequestParam LocalDate startDate,
            @Nullable @RequestParam LocalTime startTime,
            @Nullable @RequestParam LocalDate endDate,
            @Nullable @RequestParam LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}
