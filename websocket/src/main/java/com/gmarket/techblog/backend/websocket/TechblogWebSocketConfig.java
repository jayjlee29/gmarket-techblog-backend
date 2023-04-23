package com.gmarket.techblog.backend.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class TechblogWebSocketConfig {
    @Autowired
    TechblogWebSocketHandler techblogWebSocketHandler;

    @Bean
    public HandlerMapping webSocketHandlerMapping() {
        Map<String, org.springframework.web.reactive.socket.WebSocketHandler> map = new HashMap<>();
        map.put("/ws", techblogWebSocketHandler);
        int order = -1; // before annotated controllers
        return new SimpleUrlHandlerMapping(map, order);
    }

}
