package com.isd.parking.config;

import com.isd.parking.controller.ArduinoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class WebSocketHandler extends TextWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        logger.info("A user with session Id:" + session.getId() + " created a session");
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        logger.info("A user with session Id: " + session.getId() + "send message " + message.toString());
//        super.handleTextMessage(session, message);
        System.out.println("msg : " + message.getPayload());
        ArduinoController arduinoController = new ArduinoController();
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
        logger.info("A user with session Id:" + session.getId() + " changed status to " + status);
    }

}
