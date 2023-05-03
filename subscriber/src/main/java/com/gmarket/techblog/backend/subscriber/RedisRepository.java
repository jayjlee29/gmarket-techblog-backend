package com.gmarket.techblog.backend.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class RedisRepository {

    @Autowired
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate;


    public Mono<Long> save(String key, String data){
        return reactiveRedisTemplate.opsForList().rightPush(key, data);
    }

    public Flux<String> payloads(String key) {
        return reactiveRedisTemplate.opsForList().range(key, 0, -1);
    }
}
