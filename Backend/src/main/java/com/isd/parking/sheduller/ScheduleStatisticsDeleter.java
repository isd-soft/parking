package com.isd.parking.sheduller;

import com.isd.parking.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "spring.enable.scheduling")
@Slf4j
public class ScheduleStatisticsDeleter {

    @Autowired
    private StatisticsService statisticsService;

    @Scheduled(cron = "0 0 1 * * *")            //task will be executed at 13:00 every day
    public void scheduleTaskDeleteStats() {

        log.info("Delete stats schedule job executing...");

        statisticsService.deleteStatsOlderThanWeek();
    }
}
