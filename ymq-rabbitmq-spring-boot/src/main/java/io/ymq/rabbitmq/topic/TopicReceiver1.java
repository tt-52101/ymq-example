package io.ymq.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 描述: topic.ms 主题
 *
 * @author yanpenglei
 * @create 2017-10-20 15:02
 **/

@Component
@RabbitListener(queues = "topic.message")
public class TopicReceiver1 {

 @RabbitHandler
    public void process(String message) {
        System.out.println("TopicReceiver1 topic.message: " + message);
    }

}
