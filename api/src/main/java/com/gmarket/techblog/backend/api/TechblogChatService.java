package com.gmarket.techblog.backend.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class TechblogChatService {
    @Autowired
    private TechblogChatRepository techblogChatRepository;
    public Flux<String> getSessions() {
        return techblogChatRepository.getSessions()
                .doOnNext(key->{
                    log.info("session {}", key);
                });
    }
}
