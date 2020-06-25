package ru.job4j;

import org.junit.Test;
import ru.job4j.quartz.AlertRabbit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AlertRabbitTest {
    @Test
    public void timeInterval() {
        String path = "./src/main/resources/rabbit.properties";
        int result = AlertRabbit.timeInterval(path);
        assertThat(result, is(10));
    }
}
