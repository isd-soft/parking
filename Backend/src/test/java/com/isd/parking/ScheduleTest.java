package com.isd.parking;

import com.isd.parking.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import static org.mockito.Mockito.mock;


/**
 * Scheduling tasks test
 */
@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "spring.enable.scheduling")
@Slf4j
public class ScheduleTest {


    /**
     * Sample scheduling tasks test
     */
    @Test
    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() {
        StatisticsService statisticsService = mock(StatisticsService.class);
        log.info("Fixed delay task executing - " + System.currentTimeMillis() / 1000);
        statisticsService.deleteStatsOlderThanWeek();
    }
}
