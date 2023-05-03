package com.gmarket.techblog.backend.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RedisPublisher {

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveTemplate;

    public Mono<Boolean> publish(String topic, String message) {

        return reactiveTemplate.convertAndSend(topic, message).flatMap(c->{

            log.info("convertAndSend {}", c);
            if(c>0) return Mono.just(true);

            return Mono.just(false);
        });
    }

    public Flux<String> getData(String topic) {
        return reactiveTemplate.opsForList().range(topic, 0, -1);
    }

}
