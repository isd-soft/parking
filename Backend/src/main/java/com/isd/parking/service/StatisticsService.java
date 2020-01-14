package com.isd.parking.service;

import com.isd.parking.model.StatisticsRecord;
import com.isd.parking.repository.StatisticsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class StatisticsService {

    @Autowired
    private StatisticsRepo statisticsRepo;

    public List<StatisticsRecord> listAll() {

        log.info("Service get statistics list executed...");

        return statisticsRepo.findAll();
    }

    @Transactional
    public void deleteStatsOlderThanWeek() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        java.sql.Date oneWeek = new java.sql.Date(cal.getTimeInMillis());

        log.info("Service delete statistics older than one week executed...");

        statisticsRepo.removeOlderThan(oneWeek);

    }

    @Transactional
    public StatisticsRecord save(StatisticsRecord statisticsRecord) {

        log.info("Service save statistics event executed...");

        return statisticsRepo.save(statisticsRecord);
    }
}
