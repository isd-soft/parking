package com.isd.parking.config;

import com.isd.parking.controller.ArduinoController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ArduinoController arduinoController;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("A user with session Id:" + session.getId() + " created a session");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        log.info("A user with session Id: " + session.getId() + "send message " + message.toString());
//        super.handleTextMessage(session, message);
        System.out.println("msg : " + message.getPayload());

        //message format
        /*
            {\"lot_number\":1,\"status\":2}
         */

        switch (message.getPayload()) {
            case "FREE":
                System.out.println("Changing status to FREE");
                break;
            case "OCCUPIED":
                System.out.println("Changing status to OCCUPIED");
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("A user with session Id:" + session.getId() + " changed status to " + status);
    }

}
