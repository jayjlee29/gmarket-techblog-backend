package com.gmarket.techblog.backend.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RedisService {

    @Autowired
    RedisRepository redisRepository;

    public Mono<Boolean> savePayload(String key, String payload) {

        return redisRepository.save(key, payload)
                .map(count->{
                    log.info("saved topic payload {} : {} - {}", key, payload, count);
                    return Boolean.TRUE;
                }).onErrorResume(err->{
                    log.error("error save Payload", err);
                    return Mono.just(Boolean.FALSE);
                });
    }
}
