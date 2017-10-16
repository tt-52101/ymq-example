package io.ymq.rabbitmq.test;

import io.ymq.rabbitmq.Sender;
import io.ymq.rabbitmq.run.Startup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-10-16 17:06
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class BaseTest {

    @Autowired
    private Sender sender;

    @Test
    public void test() throws Exception {

        sender.send("www.ymq.io");
    }
}