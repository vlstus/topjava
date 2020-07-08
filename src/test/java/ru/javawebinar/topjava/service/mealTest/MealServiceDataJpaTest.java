package ru.javawebinar.topjava.service.mealTest;


import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;

@ActiveProfiles("datajpa")
public class MealServiceDataJpaTest extends MealServiceTest {

    @Test
    public void getByIdAndUserIdEagerTest() {
        Meal byIdAndUserIdEager = service.getByIdAndUserIdEager(100002, 100000);
        org.assertj.core.api.Assertions.assertThat(byIdAndUserIdEager.getId()).isEqualTo(100002);
        org.assertj.core.api.Assertions.assertThat(byIdAndUserIdEager.getUser().getId()).isEqualTo(100000);
    }
}
