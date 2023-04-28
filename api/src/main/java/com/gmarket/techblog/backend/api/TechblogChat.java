package com.gmarket.techblog.backend.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

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
}
