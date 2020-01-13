package com.isd.parking.service;

import com.isd.parking.model.StatsRow;
import com.isd.parking.repository.StatsRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class StatsService {

    @Autowired
    private StatsRepo statsRepo;

    public List<StatsRow> listAll() {

        log.info("Service get statistics list executed...");

        return statsRepo.findAll();
    }

    public void deleteStatsOlderThanWeek() {

        log.info("Service delete statistics older than one week executed...");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);

        java.sql.Date oneWeek = new java.sql.Date(cal.getTimeInMillis());

        statsRepo.removeOlderThan(oneWeek);

    }

    public StatsRow save(StatsRow statsRow) {

        log.info("Service save statistics event executed...");

        return statsRepo.save(statsRow);
    }
}
