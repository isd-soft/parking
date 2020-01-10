package com.isd.parking.service;

import com.isd.parking.model.StatsRow;
import com.isd.parking.repository.StatsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class StatsService {

    private static final Logger LOG = LoggerFactory.getLogger(StatsService.class);

    @Autowired
    private StatsRepo statsRepo;

    public List<StatsRow> listAll() {
        return statsRepo.findAll();
    }

    public void deleteStatsOlderThanWeek() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);

        java.sql.Date oneWeek = new java.sql.Date(cal.getTimeInMillis());

        statsRepo.removeOlderThan(oneWeek);

    }
}
