package com.isd.parking.controller;

import com.isd.parking.api.EndpointsAPI;
import com.isd.parking.model.StatsRow;
import com.isd.parking.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EndpointsAPI.API)
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping(EndpointsAPI.STATISTICS)
    public List<StatsRow> getAllStats() {
        return statsService.listAll();
    }
}
