package com.gmarket.techblog.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
public class TechblogChatRepository {

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    private Duration expire = Duration.ofMinutes(10);


    public Flux<String> getSessions() {
        return redisTemplate.keys("*");
    }
}
