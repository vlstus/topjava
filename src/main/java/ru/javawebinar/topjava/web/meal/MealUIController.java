package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.to.MealTo;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/profile/meals")
public class MealUIController extends AbstractMealController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestParam @Valid MealTo mealTo) {
        if (mealTo.isNew()) {
            super.create(mealTo);
        } else {
            super.update(mealTo, mealTo.id());
        }
    }

    @Override
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(
            @RequestParam @Nullable LocalDate startDate,
            @RequestParam @Nullable LocalTime startTime,
            @RequestParam @Nullable LocalDate endDate,
            @RequestParam @Nullable LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}