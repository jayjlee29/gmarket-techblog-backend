package com.gmarket.techblog.backend.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
public class RedisSubscriber {

    private ReactiveRedisMessageListenerContainer reactiveMsgListenerContainer;
    @Autowired
    private ReactiveRedisConnectionFactory connectionFactory;

    @PostConstruct
    void init() {
        log.info("init ReactiveRedisMessageListenerContainer ");
        reactiveMsgListenerContainer = new ReactiveRedisMessageListenerContainer(connectionFactory);

    }


//    @Bean
//    public void listenTopic() {
//
//        reactiveMsgListenerContainer.receive(ChannelTopic.of("myTopic"))
//                .map(p->{
//                    return p.getMessage();
//                })
//                .map(m -> {
//                    log.info("message : {}", m.toString());
//                    return m.length();
//                })
//                .switchIfEmpty(Mono.error(new IllegalArgumentException()))
//                .subscribe(c-> log.info(" count : " + c), (err)->{
//                    log.error("subscription error", err);
//                } , () -> log.info("complete rev."));
//    }


    @GetMapping("/subscribe/{topic}")
    public Flux<String> subscribe(@PathVariable("topic") String topic) {

        ChannelTopic channelTopic = ChannelTopic.of(topic);

        log.info("Starting to receive Redis Messages from Channel '" + channelTopic.getTopic() + "'.");
        return reactiveMsgListenerContainer
                .receive(channelTopic)
                .map(ReactiveSubscription.Message::getMessage)
                .map(msg -> {
                    log.info("New Message received: '" + msg.toString() + "'.");
                    return msg.toString() + "\n";
                });
    }
}
