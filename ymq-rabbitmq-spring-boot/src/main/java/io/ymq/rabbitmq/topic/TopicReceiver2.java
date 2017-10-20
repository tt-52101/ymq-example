package io.ymq.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RabbitListener(queues = "topic.message.s")
public class TopicReceiver2 {

   @RabbitHandler
    public void process(String messages) {
        System.out.println("TopicReceiver2 topic.message.s: " + messages);
    }

}
