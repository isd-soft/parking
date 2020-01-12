package com.isd.parking;

import com.isd.parking.service.StatsService;
import com.isd.parking.sheduller.ScheduleStatisticsDeleter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import static org.mockito.Mockito.mock;

@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "spring.enable.scheduling")
public class ScheduleTest {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleStatisticsDeleter.class);

    @Test
    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() {
        StatsService statsService = mock(StatsService.class);

        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
        statsService.deleteStatsOlderThanWeek();
    }
}
