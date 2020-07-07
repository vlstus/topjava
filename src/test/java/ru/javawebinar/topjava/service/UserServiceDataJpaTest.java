package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("datajpa")
public class UserServiceDataJpaTest extends UserServiceTest {

    @Test
    public void getAllWithMeals() {
        org.assertj.core.api.Assertions.assertThat(service.getWithAllMeals(100000).getMeals().size()).isEqualTo(7);
        org.assertj.core.api.Assertions.assertThat(service.getWithAllMeals(100001).getMeals().size()).isEqualTo(2);
    }
}
