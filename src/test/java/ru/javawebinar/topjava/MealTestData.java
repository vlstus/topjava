package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 100;
    public static final int MEAL_ID = START_SEQ + 2;
    public static final int USER_ID = 100000;


    public static final List<Meal> meal = List.of(
            new Meal(MEAL_ID, LocalDateTime.of(2019, 5, 8, 12, 51, 14), "Meal1", 200),
            new Meal(MEAL_ID + 1, LocalDateTime.of(2019, 8, 12, 6, 51, 14), "Meal2", 400),
            new Meal(MEAL_ID + 2, LocalDateTime.of(2019, 2, 18, 6, 51, 14), "Meal3", 500),
            new Meal(MEAL_ID + 3, LocalDateTime.of(2019, 4, 20, 14, 51, 14), "Meal4", 760),
            new Meal(MEAL_ID + 4, LocalDateTime.of(2019, 4, 20, 14, 51, 14), "Meal5", 760));

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "new", 100);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal.get(0).getId(), meal.get(0).getDateTime(), meal.get(0).getDescription(), meal.get(0).getCalories());
        updated.setDescription("Updated");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
//        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user_id");
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
