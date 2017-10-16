package io.ymq.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 描述: 发送消息
 *
 * @author yanpenglei
 * @create 2017-10-16 16:50
 **/
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String context) {

        this.rabbitTemplate.convertAndSend("hello", context + ":" + new Date());
    }
}
