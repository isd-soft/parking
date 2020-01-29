package com.isd.parking.scheduler;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


/**
 * Scheduler tasks configuration class.
 * Implementation of Spring's TaskScheduler interface, wrapping a native ScheduledThreadPoolExecutor
 * Provides extendable thread pool for load balancing on different threads
 */
public class SchedulerConfig implements SchedulingConfigurer {

    // thread poll size (amount of async working threads)
    private final int POOL_SIZE = 10;

    /**
     * Configuration method used for setting up ScheduledExecutorService's pool size
     *
     * @param scheduledTaskRegistrar - standard built-in scheduledTaskRegistrar
     */

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        // Set the ScheduledExecutorService's pool size
        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-");
        threadPoolTaskScheduler.initialize();

        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
