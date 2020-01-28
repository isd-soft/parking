package com.isd.parking.controller;

import com.isd.parking.model.StatisticsRecord;
import com.isd.parking.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Statistics controller
 * Provides methods for getting all statistics records stored in database
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }


    /**
     * Statistics records get controller
     * Used to get all statistics records from database
     *
     * @return Statistics records list
     */
    @GetMapping("/statistics")
    public List<StatisticsRecord> getAllStats() {
        log.info("Controller get all statistics executed...");
        return statisticsService.listAll();
    }
}
