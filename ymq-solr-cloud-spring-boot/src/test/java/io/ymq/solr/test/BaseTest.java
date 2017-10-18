package io.ymq.solr.test;

import io.ymq.solr.YmqRepository;
import io.ymq.solr.po.Ymq;
import io.ymq.solr.run.Startup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述: 测试 solr cloud
 *
 * @author yanpenglei
 * @create 2017-10-17 19:00
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class BaseTest {

    @Autowired
    private YmqRepository ymqRepository;

    @Test
    public void test() throws Exception {

        Ymq ymq = new Ymq();
        ymq.setYmqId("1");
        ymq.setYmqTitle("test");
        ymq.setYmqUrl("www.ymq.io");
        ymq.setYmqContent("test content");
        ymq.setYmqText("text content");

        ymqRepository.save(ymq);

    }
}
