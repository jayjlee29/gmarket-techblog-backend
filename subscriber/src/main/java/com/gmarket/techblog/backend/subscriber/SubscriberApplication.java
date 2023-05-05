package com.gmarket.techblog.backend.subscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class SubscriberApplication {

    public static void main(String[] args) {
        ReactorDebugAgent.init();
        SpringApplication.run(SubscriberApplication.class, args);
    }

}
