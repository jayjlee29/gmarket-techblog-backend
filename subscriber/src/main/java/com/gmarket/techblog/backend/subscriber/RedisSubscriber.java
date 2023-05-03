package com.gmarket.techblog.backend.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
public class RedisSubscriber {

    private ReactiveRedisMessageListenerContainer reactiveMsgListenerContainer;
    @Autowired
    private ReactiveRedisConnectionFactory connectionFactory;

    @Value("${topic:serverTopic}")
    private String initTopic;

    @Autowired
    private RedisService redisService;

    @PostConstruct
    void init() {
        log.info("init ReactiveRedisMessageListenerContainer '{}' ", initTopic);
        reactiveMsgListenerContainer = new ReactiveRedisMessageListenerContainer(connectionFactory);
        topicSubscriber(initTopic).subscribe();
    }

    private Flux<String> topicSubscriber(String topic) {
        return reactiveMsgListenerContainer
                .receive(ChannelTopic.of(topic))
                .map(ReactiveSubscription.Message::getMessage)
                .map(msg -> {
                    log.info("[{}] New Message received: '{}'.", topic, msg.toString());
                    return msg.toString();
                })
                .flatMap(message->{
                    return redisService.savePayload(initTopic, message).map(succ->message);
                })
                .onErrorContinue((err, consumer)->{
                    log.error("error consumer : " + consumer, err);
                });
    }


    @GetMapping("/subscribe/{topic}")
    public Flux<String> subscribe(@PathVariable("topic") String topic) {
        log.info("Starting to receive Redis Messages from Channel '{}'.", topic);
        return topicSubscriber(topic).flatMap(message-> Mono.just(message + "\n"));
    }

}
