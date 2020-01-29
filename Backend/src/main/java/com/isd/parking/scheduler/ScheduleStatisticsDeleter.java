package com.isd.parking.scheduler;

import com.isd.parking.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "spring.enable.scheduling")
@Slf4j
public class ScheduleStatisticsDeleter {

    private final StatisticsService statisticsService;

    @Autowired
    public ScheduleStatisticsDeleter(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Scheduled(cron = "0 0 1 * * *")            //task will be executed at 13:00 every day
    public void scheduleTaskDeleteStats() {

        log.info("Delete stats schedule job executing...");

        statisticsService.deleteStatsOlderThanWeek();
    }

    //for resolve conflict launching schedule in main thread
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(4);               //change this for increase amount of threads
        scheduler.setThreadNamePrefix("scheduled-task-");
        scheduler.setDaemon(true);

        return scheduler;
    }
}
