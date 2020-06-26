package ru.job4j;

import org.junit.Test;
import ru.job4j.quartz.AlertRabbit;

import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AlertRabbitTest {

    @Test
    public void getProperties() {
        AlertRabbit ar = new AlertRabbit();
        Properties properties = ar.getProperties();
        int expected = 10;
        int result = Integer.parseInt(properties.getProperty("rabbit.interval"));
        assertThat(result, is(expected));
    }
}
