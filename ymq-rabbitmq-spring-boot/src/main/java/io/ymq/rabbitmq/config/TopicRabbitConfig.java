package io.ymq.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:配置队列
 *
 * @author yanpenglei
 * @create 2017-10-16 16:47
 **/

@Configuration
public class TopicRabbitConfig {

    @Bean
    public Queue queueHello() {
        return new Queue("hello");
    }

    final static String message = "topic.message";
    final static String messages = "topic.message.s";

    final static String ymq = "topic.ymq";

    @Bean
    public Queue queueMessage() {
        return new Queue(TopicRabbitConfig.message);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(TopicRabbitConfig.messages);
    }

    @Bean
    public Queue queueYmq() {
        return new Queue(TopicRabbitConfig.ymq);
    }

    /**
     * 交换机(Exchange) 描述：接收消息并且转发到绑定的队列，交换机不存储消息
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message.#");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        //綁定队列 queueMessages() 到 topicExchange 交换机
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.message.s");
    }
    @Bean
    Binding bindingExchangeYmq(Queue queueYmq, TopicExchange exchange) {
        return BindingBuilder.bind(queueYmq).to(exchange).with("topic.#");
    }

}
