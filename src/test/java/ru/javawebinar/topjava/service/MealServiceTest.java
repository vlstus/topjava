package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(meal, MealTestData.meal.stream()
                .filter(testData -> testData.getId().equals(MEAL_ID))
                .findAny()
                .orElse(null));
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, NOT_FOUND));
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, NOT_FOUND));
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID + 1));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertNull(repository.get(MEAL_ID, USER_ID));
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID + 1, NOT_FOUND));
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID + 2, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        org.assertj.core.api.Assertions.assertThat(service.getBetweenInclusive(LocalDate.of(2018, Month.JANUARY, 15), LocalDate.of(2020, Month.JANUARY, 15), USER_ID)).isNotEmpty();
        org.assertj.core.api.Assertions.assertThat(service.getBetweenInclusive(LocalDate.now(), LocalDate.now().plus(1, ChronoUnit.MONTHS), USER_ID)).isEmpty();
    }

    @Test
    public void getAll() {
        assertNotNull(service.getAll(USER_ID));
        org.assertj.core.api.Assertions.assertThat(service.getAll(NOT_FOUND)).isEmpty();
        List<Meal> mealsForUser = meal.stream()
                .filter(testMeal -> testMeal.getDescription().equals("Meal1") || testMeal.getDescription().equals("Meal2") || testMeal.getDescription().equals("Meal5"))
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
        assertMatch(service.getAll(USER_ID), mealsForUser);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MealTestData.assertMatch(service.get(updated.getId(), USER_ID), updated);
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID + 1));
    }

    @Test
    public void create() {
        Meal aNew = getNew();
        Meal created = service.create(aNew, USER_ID);
        org.assertj.core.api.Assertions.assertThat(service.get(created.getId(), USER_ID))
                .isEqualToComparingOnlyGivenFields(aNew, "description", "calories");
    }
}