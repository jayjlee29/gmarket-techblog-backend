package com.gmarket.techblog.backend.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TechblogWebSocketHandler implements WebSocketHandler {

    @Autowired
    private ReactiveRedisConnectionFactory connectionFactory;

    private ReactiveRedisMessageListenerContainer reactiveMsgListenerContainer;

    @PostConstruct
    void init() {
        log.info("init ReactiveRedisMessageListenerContainer ");
        reactiveMsgListenerContainer = new ReactiveRedisMessageListenerContainer(connectionFactory);

    }

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        Flux<WebSocketMessage> output = webSocketSession.receive()
                .doOnNext(message -> {
                    log.info("new message received {} - {}", webSocketSession.getId(), message.getPayloadAsText());
                })
                .flatMap(message -> {
                    //return webSocketSession.textMessage("Echo " + value);
                    String topic = message.getPayloadAsText();
                    return reactiveMsgListenerContainer
                            .receive(ChannelTopic.of(topic))
                            .map(ReactiveSubscription.Message::getMessage)
                            .map(msg -> {
                                return webSocketSession.textMessage("Echo : " + msg);
                            });
                });

        return webSocketSession.send(output);
    }
}
