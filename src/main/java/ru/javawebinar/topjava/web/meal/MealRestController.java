package ru.javawebinar.topjava.web.meal;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;


import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import static ru.javawebinar.topjava.util.MealsUtil.getEntities;


@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @GetMapping
    public List<Meal> getAllEntities() {
        return getEntities(super.getAll());
    }

    @Override
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal createdMeal = super.create(meal);
        URI uriOfCreatedMeal = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(createdMeal.getId()).toUri();
        return ResponseEntity.created(uriOfCreatedMeal).body(createdMeal);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    @GetMapping(params = {"startDate", "startTime", "endDate", "endTime"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Meal> getEntitiesBetween(
            @Nullable @RequestParam LocalDate startDate,
            @Nullable @RequestParam LocalTime startTime,
            @Nullable @RequestParam LocalDate endDate,
            @Nullable @RequestParam LocalTime endTime) {
        return getEntities(super.getBetween(startDate, startTime, endDate, endTime));
    }

}