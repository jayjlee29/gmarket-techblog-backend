package com.gmarket.techblog.backend.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class TechblogChatService {
    @Autowired
    private TechblogChatRepository techblogChatRepository;

    public Mono<Void> addTopic(String sessionId, String topic) {
        log.info("add topic {} {}", sessionId, topic);
        return techblogChatRepository.save(sessionId, topic).then();
    }

    public Mono<Boolean> remove(String sessionId) {

        return techblogChatRepository.deleteById(sessionId);
    }
}
