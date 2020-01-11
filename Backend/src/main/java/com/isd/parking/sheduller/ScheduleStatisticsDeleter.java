package com.isd.parking.sheduller;

import com.isd.parking.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;


@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "spring.enable.scheduling")
public class ScheduleStatisticsDeleter {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleStatisticsDeleter.class);

    @Autowired
    private StatsService statsService;

    @Scheduled(cron = "0 0 1 * * *")            //task will be executed at 13:00 every day
    public void scheduleTaskDeleteStats() {

        LOG.info("Delete stats shedule job executing...");

        statsService.deleteStatsOlderThanWeek();
    }
}
