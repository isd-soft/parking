package com.isd.parking.controller;

import com.isd.parking.model.ParkingLot;
import com.isd.parking.model.StatisticsRecord;
import com.isd.parking.model.enums.ParkingLotStatus;
import com.isd.parking.service.ParkingLotLocalService;
import com.isd.parking.service.ParkingLotService;
import com.isd.parking.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.sql.Date;
import java.util.Optional;


@Slf4j
@Component
public class ArduinoWebSocketHandler extends TextWebSocketHandler {

    private final String securityToken = "4a0a8679643673d083b23f52c21f27cac2b03fa2";           //{SHA1}arduino

    private final ParkingLotService parkingLotService;

    private final ParkingLotLocalService parkingLotLocalService;

    private final StatisticsService statisticsService;

    @Autowired
    public ArduinoWebSocketHandler(ParkingLotService parkingLotService, ParkingLotLocalService parkingLotLocalService, StatisticsService statisticsService) {
        this.parkingLotService = parkingLotService;
        this.parkingLotLocalService = parkingLotLocalService;
        this.statisticsService = statisticsService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        log.info("A user with session Id:" + session.getId() + " created a session");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {

        log.info("Session Id: " + session.getId() + ", message body" + message.toString());

        JSONObject msgObject = new JSONObject(message.getPayload());
        String arduinoToken = msgObject.getString("token");

        if (arduinoToken.equals(securityToken)) {

            String lotId = msgObject.getString("id");
            String parkingLotStatus = msgObject.getString("status");

            Optional<ParkingLot> parkingLotOptional = parkingLotService.findById(Long.valueOf(lotId));

            parkingLotOptional.ifPresent(parkingLot -> {

                parkingLot.setStatus(ParkingLotStatus.valueOf(parkingLotStatus));
                parkingLot.setUpdatedNow();

                parkingLotService.save(parkingLot);
                parkingLotLocalService.save(parkingLot);

                StatisticsRecord statisticsRecord = StatisticsRecord.builder()
                        .lotNumber(parkingLot.getNumber())
                        .status(parkingLot.getStatus())
                        .updatedAt(new Date(System.currentTimeMillis())).build();

                statisticsService.save(statisticsRecord);
            });
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        log.info("Session Id:" + session.getId() + " changed status to " + status);
    }
}
