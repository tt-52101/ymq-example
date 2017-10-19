package io.ymq.mybatis.test;


import io.ymq.mybatis.run.Startup;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void test() throws Exception {

    }
}