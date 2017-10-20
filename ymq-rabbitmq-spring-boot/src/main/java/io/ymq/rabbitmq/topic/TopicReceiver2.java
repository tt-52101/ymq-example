package io.ymq.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 描述: topic.mss 主题
 *
 * @author yanpenglei
 * @create 2017-10-20 15:02
 **/

@Component
@RabbitListener(queues = "topic.messages")
public class TopicReceiver2 {

   @RabbitHandler
    public void process(String messages) {
        System.out.println("TopicReceiver2 topic.messages: " + messages);
    }

}
