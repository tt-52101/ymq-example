package io.ymq.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:配置队列
 *
 * @author yanpenglei
 * @create 2017-10-16 16:47
 **/

@Configuration
public class YmqBaseQueue {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }
}
