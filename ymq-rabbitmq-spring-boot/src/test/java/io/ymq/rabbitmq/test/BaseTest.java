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

    @Test
    public void test1() throws Exception {

        sender.sendMS1("此消息，只有Receiver1 Receiver3 可以收到");
    }

    @Test
    public void test2() throws Exception {

        sender.sendMS2("此消息，只有Receiver2 Receiver3 可以收到");
    }
    @Test
    public void test3() throws Exception {

        sender.sendMS3("此消息，只有 Receiver3 可以收到");
    }
}