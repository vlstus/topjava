package ru.javawebinar.topjava.service.jdbc;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {
//    @Ignore("Ignoring validation tests for jdbc impl due to no hibernate validation dependency present")
    @Test
    @Override
    public void createWithException() throws Exception {
        super.createWithException();
    }
}