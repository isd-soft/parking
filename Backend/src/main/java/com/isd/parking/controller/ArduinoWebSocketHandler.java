package com.isd.parking.controller;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.StatisticsRecord;
import com.isd.parking.model.enums.ParkingLotStatus;
import com.isd.parking.repository.ParkingLotRepository;
import com.isd.parking.service.ParkingLotService;
import com.isd.parking.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.sql.Date;
import java.util.Optional;


@Slf4j
@Component
public class ArduinoWebSocketHandler extends TextWebSocketHandler {

    private final String securityToken = "4a0a8679643673d083b23f52c21f27cac2b03fa2";           //{SHA1}arduino

    private final ParkingLotRepository parkingLotService;

    private final StatisticsService statisticsService;

    @Autowired
    public ArduinoWebSocketHandler(ParkingLotRepository parkingLotService, StatisticsService statisticsService) {
        this.parkingLotService = parkingLotService;
        this.statisticsService = statisticsService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("A user with session Id:" + session.getId() + " created a session");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {

//        try {
//            super.handleTextMessage(session, message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        log.info("Session Id: " + session.getId() + ", message body" + message.toString());
        log.info("Message : " + message.getPayload());

        //message sample
        /*
            {"mBody":"Arduino data", "id":"1", "status":"FREE", "token":"4a0a8679643673d083b23f52c21f27cac2b03fa2"};
         */

        //parsing data from message
        JSONObject msgObject = new JSONObject(message.getPayload());

        String arduinoToken = msgObject.getString("token");

        if (arduinoToken.equals(securityToken)) {

            log.info("Handle text message from arduino");

            String lotId = msgObject.getString("id");
            log.info("Parking lot number: " + lotId);

            String parkingLotStatus = msgObject.getString("status");
            log.info("Parking lot status: " + parkingLotStatus);

            Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(Long.valueOf(lotId));

            //if lot with this number exists in database
            parkingLotOptional.ifPresent(parkingLot -> {

                log.info("Parking lot found in database: " + parkingLot);

                parkingLot.setStatus(ParkingLotStatus.valueOf(parkingLotStatus));       //get enum value from string
                parkingLot.setUpdatedNow();

                log.info("Updated parking lot: " + parkingLot);

                parkingLotService.save(parkingLot);

                //save new statistics to database
                StatisticsRecord statisticsRecord = StatisticsRecord.builder()//.id(UUID.randomUUID())
                        .lotNumber(parkingLot.getNumber())
                        .status(parkingLot.getStatus())
                        .updatedAt(new Date(System.currentTimeMillis())).build();

                log.info("Statistics record: " + statisticsRecord);

                log.info("Controller update statistics executed...");

                statisticsService.save(statisticsRecord);
            });
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Session Id:" + session.getId() + " changed status to " + status);
    }

}
