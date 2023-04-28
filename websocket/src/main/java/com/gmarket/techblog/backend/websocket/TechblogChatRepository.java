package com.gmarket.techblog.backend.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Repository
public class TechblogChatRepository {

    private Duration expire = Duration.ofMinutes(10);
    @Autowired
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    

    public Mono<Boolean> save(String id, String topic){
        return this.reactiveRedisTemplate.opsForList().rightPush(id, topic)
                .flatMap(count->{
                    return this.reactiveRedisTemplate.expire(id, expire);
                });
    }

    public Mono<Boolean> deleteById(String id) {
        return this.reactiveRedisTemplate.opsForList().delete(id);
    }

}
