package ru.javawebinar.topjava.service.userServiceTest;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.TestServiceLogUtil;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    @BeforeClass
    public static void beforeClass() throws Exception {
        TestServiceLogUtil.logInfo(JDBC);
    }
}