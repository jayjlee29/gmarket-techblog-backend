package com.gmarket.techblog.backend.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class TechblogRedisConfig {

    @Autowired
    private ReactiveRedisConnectionFactory connectionFactory;

    @Bean
    public ReactiveRedisMessageListenerContainer reactiveRedisMessageListenerContainer() {
        ReactiveRedisMessageListenerContainer reactiveMsgListenerContainer = new ReactiveRedisMessageListenerContainer(connectionFactory);

        return reactiveMsgListenerContainer;
    }

    @Bean
    ReactiveRedisOperations<String, TechblogChat> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<TechblogChat> serializer = new Jackson2JsonRedisSerializer<>(TechblogChat.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, TechblogChat> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, TechblogChat> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
