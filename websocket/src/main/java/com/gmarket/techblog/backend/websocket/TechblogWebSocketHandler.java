package com.gmarket.techblog.backend.websocket;

import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

@Slf4j
@Component
public class TechblogWebSocketHandler implements WebSocketHandler {

    @Autowired
    private ReactiveRedisMessageListenerContainer reactiveMsgListenerContainer;

    @Autowired
    private TechblogChatService techblogChatService;

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        String sessionId = webSocketSession.getId();

        Flux<WebSocketMessage> output = webSocketSession.receive()
            .doFirst(()->{
                log.info("connect - new session {}", sessionId);
            })
            .flatMap(message->{
                String topic = message.getPayloadAsText();
                log.info("message - {} topic is {}", sessionId, message.getPayloadAsText());

                return techblogChatService.addTopic(sessionId, topic).flatMapMany(it->{
                    return reactiveMsgListenerContainer
                            .receive(ChannelTopic.of(topic))
                            .map(ReactiveSubscription.Message::getMessage)
                            .map(msg -> {
                                return webSocketSession.textMessage("Echo : " + msg);
                            });
                });

            })
            .doFinally(sig -> {
                log.info("close - terminating ession (client side) sig: [{}], [{}]", sig.name(), sessionId);
                techblogChatService.remove(sessionId);
                webSocketSession.close();

            })
            .doOnError(err->{
                log.error("doOnError", err);
            });

        return webSocketSession.send(output);
    }
}
