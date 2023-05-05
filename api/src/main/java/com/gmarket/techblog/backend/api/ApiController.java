package com.gmarket.techblog.backend.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
public class ApiController {
    @Autowired
    RedisPublisher redisPublisher;

    @GetMapping("/")
    public Mono<String> index() {
        return Mono.just("hello redis api");
    }
    @GetMapping("/publish/{topic}")
    public Mono<Boolean> publishMessage(@PathVariable("topic") String topic, @RequestParam String payload) {
        return redisPublisher.publish(topic, payload);
    }

    @GetMapping(value = "/data/{topic}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CharSequence> getData(@PathVariable("topic") String topic) {
        return redisPublisher.getData(topic).flatMap(message->Mono.just(message));
    }
}
