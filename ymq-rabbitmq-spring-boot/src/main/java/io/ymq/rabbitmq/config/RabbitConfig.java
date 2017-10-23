package io.ymq.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:配置普通队列
 *
 * @author yanpenglei
 * @create 2017-10-16 16:47
 **/
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queueHello() {
        return new Queue("hello");
    }

}
