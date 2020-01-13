package com.isd.parking.controller;

import com.isd.parking.model.StatsRow;
import com.isd.parking.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class StatsController {

    @Autowired
    private StatsService statsService;


    @GetMapping("/statistics")
    public List<StatsRow> getAllStats() {

        log.info("Controller get all statistics executed...");

        return statsService.listAll();
    }
}
