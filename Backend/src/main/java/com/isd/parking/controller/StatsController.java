package com.isd.parking.controller;

import com.isd.parking.model.StatsRow;
import com.isd.parking.service.StatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatsController {

    private static final Logger LOG = LoggerFactory.getLogger(StatsController.class);

    @Autowired
    private StatsService statsService;

    @GetMapping("/statistics")
    public List<StatsRow> getAllStats() {

        LOG.info("Controller get all statistics executed...");

        return statsService.listAll();
    }
}
