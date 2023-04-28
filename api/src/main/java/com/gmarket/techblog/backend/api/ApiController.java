package com.gmarket.techblog.backend.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ApiController {
    @Autowired
    RedisPublisher redisPublisher;

    @Autowired
    TechblogChatService techblogChatService;

    @GetMapping("/")
    public Mono<String> index() {
        return Mono.just("hello redis api");
    }
    @GetMapping("/publish/{topic}")
    public Mono<Boolean> publishMessage(@PathVariable("topic") String topic, @RequestParam String payload) {

        log.info("{} : {}", topic, payload);
        return redisPublisher.publish(topic, payload);
    }

    @GetMapping("/sessions")
    public Flux<String> sessions() {

        return techblogChatService.getSessions();
    }
}
