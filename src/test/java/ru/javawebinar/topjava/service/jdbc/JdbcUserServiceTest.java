package ru.javawebinar.topjava.service.jdbc;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Ignore("Ignoring validation tests for jdbc impl due to no hibernate validation dependency present")
    @Test
    @Override
    public void createWithException() throws Exception {
        super.createWithException();
    }
}