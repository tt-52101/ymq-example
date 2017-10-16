package io.ymq.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 描述:消息生产者
 *
 * @author yanpenglei
 * @create 2017-10-16 18:28
 **/
@Component
public class MsgProducer {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topicName, String jsonData) {
        log.debug("向kafka推送数据:[{}]", jsonData);
        try {
            kafkaTemplate.send(topicName, jsonData);
        } catch (Exception e) {
            log.error("发送数据出错！！！{}{}", topicName, jsonData);
            log.error("发送数据出错=====>", e);
        }
        log.debug("数据发送完毕");
    }

}
