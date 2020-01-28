package com.isd.parking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;


@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated()
                .simpSubscribeDestMatchers("/arduino", "/test").permitAll()
                .simpDestMatchers("/arduino", "/test").permitAll()
                .simpSubscribeDestMatchers("/arduino", "/test").permitAll()
                .simpTypeMatchers(MESSAGE, SUBSCRIBE).permitAll()
                .anyMessage().permitAll();
    }
}
