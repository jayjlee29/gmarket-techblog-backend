package com.gmarket.techblog.backend.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RedisPublisher {

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveTemplate;

    public Mono<Boolean> publish(String topic, String message) {

        return reactiveTemplate.convertAndSend(topic, message).flatMap(c->{
            if(c>0) return Mono.just(true);

            return Mono.just(false);
        });
    }

}
