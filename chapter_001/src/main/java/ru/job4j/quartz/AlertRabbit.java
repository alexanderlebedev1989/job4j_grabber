package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {

        AlertRabbit alRabbit = new AlertRabbit();
        Properties properties = alRabbit.getProperties();

        try (Connection cn = alRabbit.getConnection(properties)) {
            Scheduler sch = alRabbit.getSchedulerBuilder(properties, cn);
            sch.start();
            Thread.sleep(10000);
            sch.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            Properties config = new Properties();
            config.load(in);
            return config;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("не удалось получить аргументы");
    }

    public Connection getConnection(Properties p) throws ClassNotFoundException, SQLException {
        Class.forName(p.getProperty("driver-class-name"));
        return DriverManager.getConnection(
                p.getProperty("url"),
                p.getProperty("username"),
                p.getProperty("password")
        );
    }

    public Scheduler getSchedulerBuilder(Properties p, Connection cn) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        JobDataMap data = new JobDataMap();
        data.put("connection", cn);
        JobDetail job = newJob(Rabbit.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(p.getProperty("rabbit.interval")))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
        return scheduler;
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            Connection cn = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (PreparedStatement ps = cn.prepareStatement("INSERT INTO rabbit (create_date) VALUES (?)")) {
                ps.setDate(1, new Date(System.currentTimeMillis()));
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
