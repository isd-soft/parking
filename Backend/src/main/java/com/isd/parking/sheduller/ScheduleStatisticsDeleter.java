package com.isd.parking.sheduller;

import com.isd.parking.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "spring.enable.scheduling")
public class ScheduleStatisticsDeleter {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleStatisticsDeleter.class);

    @Autowired
    private final StatsService statsService;

    public ScheduleStatisticsDeleter(final StatsService statsService) {
        this.statsService = statsService;
    }

    @Scheduled(cron = "0 0 1 * * MON")        //task will be executed weekly every Monday at 13-00
    public void sheduleTaskDeleteStats() {

        long now = System.currentTimeMillis() / 1000;
        LOG.info("Delete stats shedule job executing..." + now);

        statsService.deleteStatsOlderThanWeek();
    }

    //test method, working
    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
        statsService.deleteStatsOlderThanWeek();
    }
}
