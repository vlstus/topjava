package ru.javawebinar.topjava.service.jpa;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaMealServiceTest extends AbstractMealServiceTest {


    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setUp() {
        cacheManager.getCache("meals").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

}