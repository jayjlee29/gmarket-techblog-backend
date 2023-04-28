package com.gmarket.techblog.backend.websocket;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@RedisHash(value = "techblog", timeToLive = -1)
public class TechblogChat {

    @Id
    private String id;
    private Set<String> topics = new CopyOnWriteArraySet<String>();

    public void addTopic(String topic) {
        if(!this.topics.contains(topic))
            this.topics.add(topic);
    }
}
