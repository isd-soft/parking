package com.isd.parking.service;

import com.isd.parking.model.StatisticsRecord;
import com.isd.parking.repository.StatisticsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;


/**
 * Statistics Service class for database repository
 * Contains methods for
 * getting all statistics records,
 * delete statistics records older than one week
 */
@Service
@Slf4j
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @Autowired
    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    /**
     * Get all statistics records from database method
     *
     * @return - Statistics records list
     */
    public List<StatisticsRecord> listAll() {
        log.info("Service get statistics list executed...");
        return statisticsRepository.findAll();
    }

    /**
     * Method deletes all statistics records older than one week
     */
    @Transactional
    public void deleteStatsOlderThanWeek() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        java.sql.Date oneWeek = new java.sql.Date(cal.getTimeInMillis());

        log.info("Service delete statistics older than one week executed...");
        statisticsRepository.removeOlderThan(oneWeek);

    }

    /**
     * Save statistics record in database method
     *
     * @return - StatisticsRecord which was saved in database
     */
    @Transactional
    public StatisticsRecord save(StatisticsRecord statisticsRecord) {
        log.info("Service save statistics event executed...");
        return statisticsRepository.save(statisticsRecord);
    }
}
